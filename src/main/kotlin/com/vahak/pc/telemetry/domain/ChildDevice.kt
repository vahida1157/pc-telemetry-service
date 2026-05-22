package com.vahak.pc.telemetry.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(
    name = "child_devices",
    uniqueConstraints = [UniqueConstraint(columnNames = ["child_id", "device_id"])]
)
class ChildDevice(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    @Column(name = "child_id", nullable = false) val childId: UUID,
    @Column(name = "device_id", nullable = false) val deviceId: UUID,
    @Column(name = "device_name", nullable = false) var deviceName: String,
    @UpdateTimestamp var lastSyncAt: Instant? = null
)