package com.vahak.pc.telemetry.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "app_installations")
class AppInstallation(
	@Id
	@Column(name = "install_id", nullable = false, length = 64)
	val installId: String,

	@Column(name = "user_id")
	var userId: String? = null,

	@Column(name = "install_source", length = 50)
	var installSource: String? = null,

	@Column(name = "device_model", length = 100)
	var deviceModel: String? = null,

	@Column(name = "app_version", length = 50)
	var appVersion: String? = null,

	@Column(name = "first_seen_at", nullable = false, updatable = false)
	val firstSeenAt: Instant = Instant.now(),

	@Column(name = "last_seen_at", nullable = false)
	var lastSeenAt: Instant = Instant.now()
)