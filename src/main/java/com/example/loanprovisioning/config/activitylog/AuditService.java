package com.example.loanprovisioning.config.activitylog;

import com.example.loanprovisioning.entity.AuditTrailLog;
import com.example.loanprovisioning.repository.AuditTrailLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;


@Service
@Slf4j
public class AuditService {
    public AuditService(AuditTrailLogRepository auditTrailLogRepository) {
        this.auditTrailLogRepository = auditTrailLogRepository;
    }

    private final AuditTrailLogRepository auditTrailLogRepository;

    @Async
    public void processAuditLog(HttpServletRequest request, AuditTrailLog auditTrailEntry) {
        log.info(LOG_PREFIX, "Started processing audit trail", "");
        try {
            val entityName = mapPathToEntity(auditTrailEntry.getPagePath());
            auditTrailEntry.setResource(entityName);
            auditTrailEntry.setDescription(auditTrailEntry.getActionType().name() + " operation on " + entityName);
            auditTrailLogRepository.save(auditTrailEntry);
            log.info(LOG_PREFIX, "Finished processing audit trail", "");
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Error encountered while saving audit trail", "", e);
        }
    }

    private String mapPathToEntity(String path) {
        val split = path.split("\\.");
        return split[split.length - 1];
    }

}