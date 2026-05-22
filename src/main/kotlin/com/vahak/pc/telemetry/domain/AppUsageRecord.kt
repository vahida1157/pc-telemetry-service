package com.vahak.pc.telemetry.domain

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(
    name = "app_usage_records",
    uniqueConstraints = [UniqueConstraint(columnNames = ["child_id", "device_id", "usage_date", "package_name"])]
)
class AppUsageRecord(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    @Column(name = "child_id", nullable = false) val childId: UUID,
    @Column(name = "device_id", nullable = false) val deviceId: UUID, // 🚀 NEW
    @Column(name = "usage_date", nullable = false) val usageDate: LocalDate,
    @Column(name = "package_name", nullable = false) val packageName: String,
    @Column(name = "used_seconds", nullable = false) var usedSeconds: Int = 0
)