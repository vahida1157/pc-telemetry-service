package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.DiagnosticEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DiagnosticEventRepository : JpaRepository<DiagnosticEvent, UUID>