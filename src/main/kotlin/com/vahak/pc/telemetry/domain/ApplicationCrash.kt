package com.vahak.pc.telemetry.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "application_crashes")
class ApplicationCrash(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val crashTimestamp: Long, // When it happened on the phone

    @Column(nullable = false)
    val appVersion: String,

    @Column(nullable = false)
    val androidVersion: String,

    @Column(nullable = false)
    val deviceModel: String,

    @Column(nullable = false)
    val exceptionType: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val stackTrace: String,

    @CreationTimestamp
    @Column(updatable = false)
    val receivedAt: Instant? = null // When the server received it
)