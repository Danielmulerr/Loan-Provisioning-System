package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface LogService {
    ResponseEntity<GenericResponse> fetchLogs();
}
