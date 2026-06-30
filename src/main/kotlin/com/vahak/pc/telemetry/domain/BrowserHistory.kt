package com.vahak.pc.telemetry.domain

import jakarta.persistence.*

@Entity
@Table(name = "browser_history")
class BrowserHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val childId: String,
    val url: String,
    val title: String,
    val timestamp: Long
)