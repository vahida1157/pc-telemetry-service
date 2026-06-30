package com.vahak.pc.telemetry.domain

import jakarta.persistence.*

@Entity
@Table(name = "browser_history")
class BrowserHistory(
    @Id var id: String, var childId: String, var url: String, var title: String, var timestamp: Long
)