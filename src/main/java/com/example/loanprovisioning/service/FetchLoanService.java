package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.GenericResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface FetchLoanService {

    ResponseEntity<GenericResponse> getAllLoans(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    ResponseEntity<GenericResponse> getLoanById(Long loanId);
}
