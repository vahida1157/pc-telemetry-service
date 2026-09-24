package com.vahak.pc.telemetry.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant

@Entity
@Table(
	name = "diagnostic_events", indexes = [
		// Standard B-Tree indexes for fast timeline queries
		Index(name = "idx_diag_user_time", columnList = "user_id, timestamp DESC"), Index(
			name = "idx_diag_type", columnList = "event_type, timestamp DESC"
		)]
)
class DiagnosticEvent(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

	@Column(nullable = false) var timestamp: Long,

	@Column(name = "install_id", nullable = false) var installId: String,

	@Column(name = "user_id") var userId: String?,

	@Column(name = "session_id", nullable = false) var sessionId: String,

	@Column(name = "event_type", nullable = false, length = 50) var eventType: String,

	@Column(name = "event_name", nullable = false) var eventName: String,

	@Column(name = "log_level", length = 50) var logLevel: String?,

	@Column(name = "error_message", columnDefinition = "TEXT") var errorMessage: String?,

	@Column(name = "stack_trace", columnDefinition = "TEXT") var stackTrace: String?,

	@Column(name = "battery_level", nullable = false) var batteryLevel: Float,

	@Column(name = "network_type", nullable = false, length = 50) var networkType: String,

	@Column(name = "free_ram_mb", nullable = false) var freeRamMb: Long,

	// 🚀 Forces Hibernate to create a native PostgreSQL JSONB column
	@JdbcTypeCode(SqlTypes.JSON) @Column(
		name = "metadata_json", columnDefinition = "jsonb", nullable = false
	) var metadataJson: String,

	@CreationTimestamp @Column(name = "created_at", updatable = false) var createdAt: Instant? = null
)