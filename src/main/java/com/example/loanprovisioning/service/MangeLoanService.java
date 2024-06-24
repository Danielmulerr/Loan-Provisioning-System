package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface MangeLoanService {

    ResponseEntity<GenericResponse> approveLoan(Long loanId);

    ResponseEntity<GenericResponse> rejectLoan(Long loanId);

    ResponseEntity<GenericResponse> disburseLoan(Long loanId);
}
