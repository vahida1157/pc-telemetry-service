package com.vahak.pc.telemetry.repository

import com.vahak.pc.telemetry.domain.ApplicationCrash
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApplicationCrashRepository : JpaRepository<ApplicationCrash, String>