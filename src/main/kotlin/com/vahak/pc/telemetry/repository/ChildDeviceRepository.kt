package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.ChildDevice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ChildDeviceRepository : JpaRepository<ChildDevice, UUID> {
    fun findByChildIdAndDeviceId(childId: UUID, deviceId: UUID): ChildDevice?
    fun findAllByChildId(childId: UUID): List<ChildDevice>
}