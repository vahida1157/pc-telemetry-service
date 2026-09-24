package com.vahak.pc.telemetry.service

import com.vahak.pc.telemetry.repository.DiagnosticEventRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TelemetryRetentionService(
	private val diagnosticEventRepository: DiagnosticEventRepository
) {
	// Runs every night at 3:00 AM
	@Scheduled(cron = "0 0 3 * * ?")
	@Transactional
	fun deleteOldTelemetry() {
		val ninetyDaysAgo = System.currentTimeMillis() - (90L * 24 * 60 * 60 * 1000)
		diagnosticEventRepository.bulkDeleteOlderThan(ninetyDaysAgo)
	}
}