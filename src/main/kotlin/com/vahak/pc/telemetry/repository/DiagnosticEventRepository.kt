package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.DiagnosticEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DiagnosticEventRepository : JpaRepository<DiagnosticEvent, Long> {

	@Modifying
	@Query("DELETE FROM DiagnosticEvent e WHERE e.timestamp < :cutoff")
	fun bulkDeleteOlderThan(@Param("cutoff") cutoff: Long)

}