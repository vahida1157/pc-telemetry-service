package com.vahak.pc.telemetry.controller

import com.vahak.pc.telemetry.dto.DiagnosticEventDto
import com.vahak.pc.telemetry.service.TelemetryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/diagnostics")
class DiagnosticController(
	private val telemetryService: TelemetryService
) {
	@PostMapping("/sync")
	fun syncDiagnostics(@RequestBody events: List<DiagnosticEventDto>): ResponseEntity<Void> {
		telemetryService.processDiagnosticsBatch(events)
		return ResponseEntity.ok().build()
	}
}