package com.vahak.pc.telemetry.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.vahak.pc.telemetry.domain.AppInstallation
import com.vahak.pc.telemetry.domain.DiagnosticEvent
import com.vahak.pc.telemetry.dto.DiagnosticEventDto
import com.vahak.pc.telemetry.repository.AppInstallationRepository
import com.vahak.pc.telemetry.repository.DiagnosticEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class TelemetryService(
	private val diagnosticEventRepository: DiagnosticEventRepository,
	private val appInstallationRepository: AppInstallationRepository
) {
	// 🚀 Instantiated locally to bypass Spring Autowiring completely
	private val objectMapper = ObjectMapper()

	@Transactional
	fun processDiagnosticsBatch(events: List<DiagnosticEventDto>) {
		if (events.isEmpty()) return

		events.groupBy { it.installId }.forEach { (installId, deviceEvents) ->
			val installation = appInstallationRepository.findById(installId).orElseGet {
				AppInstallation(installId = installId)
			}

			deviceEvents.mapNotNull { it.userId }.lastOrNull()?.let {
				installation.userId = it
			}

			for (event in deviceEvents) {
				try {
					val metadata: JsonNode = objectMapper.readTree(event.metadataJson)

					if (metadata.hasNonNull("app_version")) {
						installation.appVersion = metadata.get("app_version").asText()
					}
					if (metadata.hasNonNull("device_model")) {
						installation.deviceModel = metadata.get("device_model").asText()
					}
					if (event.eventName == "install_source" && metadata.hasNonNull("source")) {
						installation.installSource = metadata.get("source").asText()
					}
				} catch (_: Exception) {
					// Ignore malformed JSON strings
				}
			}

			installation.lastSeenAt = Instant.now()
			appInstallationRepository.save(installation)
		}

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
		diagnosticEventRepository.saveAll(entities)
	}
}