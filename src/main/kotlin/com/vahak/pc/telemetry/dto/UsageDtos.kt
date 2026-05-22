package com.vahak.pc.telemetry.dto

import java.time.LocalDate
import java.util.UUID

data class UsageSyncPayload(
    val deviceId: UUID,
    val deviceName: String,
    val activeChildId: UUID?,
    val dailyUsages: List<DailyUsageDto>,
    val appUsages: List<AppUsageDto>
)

data class DailyUsageDto(val childId: UUID, val date: LocalDate, val usedSeconds: Int)
data class AppUsageDto(val childId: UUID, val date: LocalDate, val packageName: String, val usedSeconds: Int)

data class GlobalUsageResponse(
    val globalDailySeconds: Map<UUID, Int>,
    val globalAppSeconds: Map<UUID, Map<String, Int>>
)