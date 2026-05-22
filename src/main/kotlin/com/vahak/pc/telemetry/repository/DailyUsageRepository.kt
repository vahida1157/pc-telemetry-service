package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.AppUsageRecord
import com.vahak.pc.telemetry.domain.DailyUsage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface DailyUsageRepository : JpaRepository<DailyUsage, UUID> {
    fun findByChildIdAndDeviceIdAndUsageDate(childId: UUID, deviceId: UUID, usageDate: LocalDate): DailyUsage?
    fun findAllByChildIdAndUsageDate(childId: UUID, usageDate: LocalDate): List<DailyUsage>
}

@Repository
interface AppUsageRecordRepository : JpaRepository<AppUsageRecord, UUID> {
    fun findByChildIdAndDeviceIdAndUsageDateAndPackageName(
        childId: UUID, deviceId: UUID, usageDate: LocalDate, packageName: String
    ): AppUsageRecord?

    fun findAllByChildIdAndUsageDate(childId: UUID, usageDate: LocalDate): List<AppUsageRecord>
}