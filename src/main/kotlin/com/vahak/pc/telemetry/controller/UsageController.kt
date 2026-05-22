package com.vahak.pc.telemetry.controller

import com.vahak.pc.telemetry.dto.AppReportResponse
import com.vahak.pc.telemetry.dto.GlobalUsageResponse
import com.vahak.pc.telemetry.dto.UsageSyncPayload
import com.vahak.pc.telemetry.service.UsageReportService
import com.vahak.pc.telemetry.service.UsageSyncService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/v1/usage")
class UsageController(
    private val usageSyncService: UsageSyncService,
    private val usageReportService: UsageReportService
) {
    @PostMapping("/sync")
    fun syncUsageData(@RequestBody payload: UsageSyncPayload): ResponseEntity<GlobalUsageResponse> {
        val globalMetrics = usageSyncService.processSyncPayload(payload)
        return ResponseEntity.ok(globalMetrics)
    }

    // 🚀 NEW: The API for the Parent's App
    @GetMapping("/report/{childId}")
    fun getUsageReport(
        @PathVariable childId: UUID,
        @RequestParam("date") date: LocalDate
    ): ResponseEntity<AppReportResponse> {
        val report = usageReportService.getDailyReport(childId, date)
        return ResponseEntity.ok(report)
    }
}