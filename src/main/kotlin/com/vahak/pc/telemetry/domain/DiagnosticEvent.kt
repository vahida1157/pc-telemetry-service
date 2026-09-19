package com.vahak.pc.telemetry.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "diagnostic_events",
    indexes = [
        // Standard B-Tree indexes for fast timeline queries
        Index(name = "idx_diag_user_time", columnList = "user_id, timestamp DESC"),
        Index(name = "idx_diag_type", columnList = "event_type, timestamp DESC")
    ]
)
class DiagnosticEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val timestamp: Long,

    @Column(name = "install_id", nullable = false)
    val installId: String,

    @Column(name = "user_id")
    val userId: String?,

    @Column(name = "session_id", nullable = false)
    val sessionId: String,

    @Column(name = "event_type", nullable = false, length = 50)
    val eventType: String,

    @Column(name = "event_name", nullable = false)
    val eventName: String,

    @Column(name = "log_level", length = 50)
    val logLevel: String?,

    @Column(name = "error_message", columnDefinition = "TEXT")
    val errorMessage: String?,

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    val stackTrace: String?,

    @Column(name = "battery_level", nullable = false)
    val batteryLevel: Float,

    @Column(name = "network_type", nullable = false, length = 50)
    val networkType: String,

    @Column(name = "free_ram_mb", nullable = false)
    val freeRamMb: Long,

    // 🚀 Forces Hibernate to create a native PostgreSQL JSONB column
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata_json", columnDefinition = "jsonb", nullable = false)
    val metadataJson: String,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null
)