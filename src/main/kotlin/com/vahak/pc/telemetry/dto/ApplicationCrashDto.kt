package com.vahak.pc.telemetry.dto

data class ApplicationCrashDto(
    val id: String,
    val timestamp: Long,
    val appVersion: String,
    val androidVersion: String,
    val deviceModel: String,
    val exceptionType: String,
    val stackTrace: String
)