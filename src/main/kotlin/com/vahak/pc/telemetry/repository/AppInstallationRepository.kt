package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.AppInstallation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppInstallationRepository : JpaRepository<AppInstallation, String>