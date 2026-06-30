package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.BrowserHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrowserHistoryRepository : JpaRepository<BrowserHistory, Long>