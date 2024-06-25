package com.example.loanprovisioning.controller;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/log")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping()
    ResponseEntity<GenericResponse> repayLoan() {
        return logService.fetchLogs();
    }
}
