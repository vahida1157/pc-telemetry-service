package com.vahak.pc.telemetry.dto

data class BrowserHistoryDto(
    val id: String,
    val url: String,
    val title: String,
    val timestamp: Long
)