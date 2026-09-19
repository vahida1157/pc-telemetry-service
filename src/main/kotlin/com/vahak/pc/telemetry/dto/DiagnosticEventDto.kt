package com.vahak.pc.telemetry.dto

data class DiagnosticEventDto(
    val timestamp: Long,
    val installId: String,
    val userId: String?,
    val sessionId: String,
    val eventType: String,
    val eventName: String,
    val logLevel: String?,
    val errorMessage: String?,
    val stackTrace: String?,
    val batteryLevel: Float,
    val networkType: String,
    val freeRamMb: Long,
    val metadataJson: String
)
