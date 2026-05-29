package com.vahak.pc.telemetry.controller
import com.vahak.pc.telemetry.dto.ApplicationCrashDto
import com.vahak.pc.telemetry.service.ApplicationCrashService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/crashes")
class ApplicationCrashController(
    private val crashService: ApplicationCrashService
) {

    @PostMapping("/sync")
    fun syncCrashLogs(@RequestBody crashLogs: List<ApplicationCrashDto>): ResponseEntity<Void> {
        try {
            crashService.saveCrashLogs(crashLogs)
            return ResponseEntity.ok().build()
        } catch (_: Exception) {
            // Return 500 so Android knows it failed and will retry later
            return ResponseEntity.internalServerError().build()
        }
    }
}