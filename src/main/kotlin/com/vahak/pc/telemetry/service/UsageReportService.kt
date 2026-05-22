package com.vahak.pc.telemetry.service

import com.vahak.pc.telemetry.dto.*
import com.vahak.pc.telemetry.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class UsageReportService(
    private val dailyUsageRepository: DailyUsageRepository,
    private val appUsageRepository: AppUsageRecordRepository,
    private val childDeviceRepository: ChildDeviceRepository
) {
    fun getDailyReport(childId: UUID, date: LocalDate): AppReportResponse {
        val dailyRecords = dailyUsageRepository.findAllByChildIdAndUsageDate(childId, date)
        val totalDailySeconds = dailyRecords.sumOf { it.usedSeconds }

        val appRecords = appUsageRepository.findAllByChildIdAndUsageDate(childId, date)
        val knownDevices = childDeviceRepository.findAllByChildId(childId).associateBy { it.deviceId }

        val groupedByApp = appRecords.groupBy { it.packageName }

        val appBreakdowns = groupedByApp.map { (packageName, records) ->
            val totalSecondsForApp = records.sumOf { it.usedSeconds }
            
            val deviceDetails = records.map { record ->
                DeviceUsageDetail(
                    deviceName = knownDevices[record.deviceId]?.deviceName ?: "Unknown Device",
                    usedSeconds = record.usedSeconds
                )
            }.sortedByDescending { it.usedSeconds }

            AppUsageBreakdown(
                packageName = packageName,
                totalSeconds = totalSecondsForApp,
                devices = deviceDetails
            )
        }.sortedByDescending { it.totalSeconds }

        return AppReportResponse(totalDailySeconds = totalDailySeconds, apps = appBreakdowns)
    }
}