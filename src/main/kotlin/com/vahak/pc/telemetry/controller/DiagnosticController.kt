package com.vahak.pc.telemetry.controller

import com.vahak.pc.telemetry.domain.DiagnosticEvent
import com.vahak.pc.telemetry.dto.DiagnosticEventDto
import com.vahak.pc.telemetry.repository.DiagnosticEventRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/diagnostics")
class DiagnosticController(
    private val repository: DiagnosticEventRepository
) {
    @PostMapping("/sync")
    fun syncDiagnostics(@RequestBody events: List<DiagnosticEventDto>): ResponseEntity<Void> {
        val entities = events.map { dto ->
            DiagnosticEvent(
                timestamp = dto.timestamp,
                installId = dto.installId,
                userId = dto.userId,
                sessionId = dto.sessionId,
                eventType = dto.eventType,
                eventName = dto.eventName,
                logLevel = dto.logLevel,
                errorMessage = dto.errorMessage,
                stackTrace = dto.stackTrace,
                batteryLevel = dto.batteryLevel,
                networkType = dto.networkType,
                freeRamMb = dto.freeRamMb,
                metadataJson = dto.metadataJson
            )
        }

        repository.saveAll(entities)
        return ResponseEntity.ok().build()
    }
}