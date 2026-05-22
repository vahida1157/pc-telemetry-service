package com.vahak.pc.telemetry.dto

data class AppReportResponse(
    val totalDailySeconds: Int,
    val apps: List<AppUsageBreakdown>
)

data class AppUsageBreakdown(
    val packageName: String,
    val totalSeconds: Int,
    val devices: List<DeviceUsageDetail>
)

data class DeviceUsageDetail(
    val deviceName: String,
    val usedSeconds: Int
)