package com.example.loanprovisioning.repository;

import com.example.loanprovisioning.entity.AuditTrailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailLogRepository extends JpaRepository<AuditTrailLog, Long> {
}