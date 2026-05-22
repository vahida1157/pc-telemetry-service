package com.vahak.pc.telemetry.service

import com.vahak.pc.telemetry.domain.AppUsageRecord
import com.vahak.pc.telemetry.domain.ChildDevice
import com.vahak.pc.telemetry.domain.DailyUsage
import com.vahak.pc.telemetry.dto.GlobalUsageResponse
import com.vahak.pc.telemetry.dto.UsageSyncPayload
import com.vahak.pc.telemetry.repository.AppUsageRecordRepository
import com.vahak.pc.telemetry.repository.ChildDeviceRepository
import com.vahak.pc.telemetry.repository.DailyUsageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*
import kotlin.math.max

@Service
class UsageSyncService(
    private val dailyUsageRepository: DailyUsageRepository,
    private val appUsageRepository: AppUsageRecordRepository,
    private val childDeviceRepository: ChildDeviceRepository // 🚀 INJECTED
) {
    @Transactional
    fun processSyncPayload(payload: UsageSyncPayload): GlobalUsageResponse {
        val today = LocalDate.now()
        val impactedChildren = mutableSetOf<UUID>()

        if (payload.activeChildId != null) impactedChildren.add(payload.activeChildId)

        // 🚀 1. UPSERT THE DEVICE NAME (So the parent sees the real phone name)
        if (payload.dailyUsages.isNotEmpty() || payload.appUsages.isNotEmpty() || payload.activeChildId != null) {
            val targetChild = payload.activeChildId ?: payload.dailyUsages.firstOrNull()?.childId ?: payload.appUsages.firstOrNull()?.childId
            if (targetChild != null) {
                val existingDevice = childDeviceRepository.findByChildIdAndDeviceId(targetChild, payload.deviceId)
                if (existingDevice != null) {
                    existingDevice.deviceName = payload.deviceName
                    childDeviceRepository.save(existingDevice)
                } else {
                    childDeviceRepository.save(ChildDevice(childId = targetChild, deviceId = payload.deviceId, deviceName = payload.deviceName))
                }
            }
        }

        // 🚀 2. PROCESS DAILY USAGES (Math.max prevents delayed packets from reversing time)
        payload.dailyUsages.forEach { dto ->
            impactedChildren.add(dto.childId)
            val existing = dailyUsageRepository.findByChildIdAndDeviceIdAndUsageDate(dto.childId, payload.deviceId, dto.date)
            if (existing != null) {
                existing.usedSeconds = max(existing.usedSeconds, dto.usedSeconds)
                dailyUsageRepository.save(existing)
            } else {
                dailyUsageRepository.save(DailyUsage(childId = dto.childId, deviceId = payload.deviceId, usageDate = dto.date, usedSeconds = dto.usedSeconds))
            }
        }

        // 🚀 3. PROCESS APP USAGES (Math.max applied here too)
        payload.appUsages.forEach { dto ->
            impactedChildren.add(dto.childId)
            val existing = appUsageRepository.findByChildIdAndDeviceIdAndUsageDateAndPackageName(dto.childId, payload.deviceId, dto.date, dto.packageName)
            if (existing != null) {
                existing.usedSeconds = max(existing.usedSeconds, dto.usedSeconds)
                appUsageRepository.save(existing)
            } else {
                appUsageRepository.save(AppUsageRecord(childId = dto.childId, deviceId = payload.deviceId, usageDate = dto.date, packageName = dto.packageName, usedSeconds = dto.usedSeconds))
            }
        }

        // 4. CALCULATE GLOBAL SUMS TO RETURN TO THE PHONE
        val globalDailyMap = mutableMapOf<UUID, Int>()
        val globalAppMap = mutableMapOf<UUID, MutableMap<String, Int>>()

        impactedChildren.forEach { childId ->
            val allDeviceDailies = dailyUsageRepository.findAllByChildIdAndUsageDate(childId, today)
            globalDailyMap[childId] = allDeviceDailies.sumOf { it.usedSeconds }

            val allDeviceApps = appUsageRepository.findAllByChildIdAndUsageDate(childId, today)
            val appTotals = mutableMapOf<String, Int>()
            allDeviceApps.forEach { record ->
                appTotals[record.packageName] = (appTotals[record.packageName] ?: 0) + record.usedSeconds
            }
            globalAppMap[childId] = appTotals
        }

        return GlobalUsageResponse(globalDailySeconds = globalDailyMap, globalAppSeconds = globalAppMap)
    }
}