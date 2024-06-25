package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.repository.AuditTrailLogRepository;
import com.example.loanprovisioning.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;

@Service
@Slf4j
public class LogsServiceImpl implements LogService {
    private final AuditTrailLogRepository auditTrailLogRepository;

    public LogsServiceImpl(AuditTrailLogRepository auditTrailLogRepository) {
        this.auditTrailLogRepository = auditTrailLogRepository;
    }

    @Override
    public ResponseEntity<GenericResponse> fetchLogs()

    {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully repay logs", auditTrailLogRepository.findAll());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while fetching logs. ", "Try again later", e);
            throw new GeneralException("Failed while fetching logs. Try again later");
        }
    }
}
