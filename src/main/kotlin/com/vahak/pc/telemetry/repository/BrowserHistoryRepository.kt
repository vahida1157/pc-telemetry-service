package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.BrowserHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrowserHistoryRepository : JpaRepository<BrowserHistory, Long> {
    fun findByChildIdAndTimestampBetweenOrderByTimestampDesc(
        childId: String, start: Long, end: Long
    ): List<BrowserHistory>
}