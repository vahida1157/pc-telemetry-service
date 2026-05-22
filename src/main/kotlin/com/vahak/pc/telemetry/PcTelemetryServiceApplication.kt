package com.vahak.pc.telemetry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PcTelemetryServiceApplication

fun main(args: Array<String>) {
    runApplication<PcTelemetryServiceApplication>(*args)
}
