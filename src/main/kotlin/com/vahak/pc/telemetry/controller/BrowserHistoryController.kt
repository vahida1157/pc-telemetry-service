package com.vahak.pc.telemetry.controller

import com.vahak.pc.telemetry.dto.BrowserHistoryDto
import com.vahak.pc.telemetry.service.BrowserHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/browser")
class BrowserHistoryController(
    private val telemetryService: BrowserHistoryService
) {

    @PutMapping("/{childId}/history")
    fun syncBrowserHistory(
        @PathVariable childId: String, @RequestBody history: List<BrowserHistoryDto>
    ): ResponseEntity<Void> {
        if (history.isNotEmpty()) {
            telemetryService.saveHistory(childId, history)
        }
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{childId}/history")
    fun getBrowserHistory(
        @PathVariable childId: String,
        @RequestParam start: Long,
        @RequestParam end: Long
    ): ResponseEntity<List<BrowserHistoryDto>> {
        val history = telemetryService.getHistoryForDateRange(childId, start, end)
        return ResponseEntity.ok(history)
    }
}