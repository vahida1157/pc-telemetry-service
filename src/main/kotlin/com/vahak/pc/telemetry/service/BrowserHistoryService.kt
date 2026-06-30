package com.vahak.pc.telemetry.service

import com.vahak.pc.telemetry.domain.BrowserHistory
import com.vahak.pc.telemetry.dto.BrowserHistoryDto
import com.vahak.pc.telemetry.repository.BrowserHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrowserHistoryService(
    private val historyRepo: BrowserHistoryRepository
) {
    @Transactional
    fun saveHistory(childId: String, historyList: List<BrowserHistoryDto>) {
        val entities = historyList.map { 
            BrowserHistory(childId = childId, url = it.url, title = it.title, timestamp = it.timestamp)
        }
        historyRepo.saveAll(entities)
    }
}