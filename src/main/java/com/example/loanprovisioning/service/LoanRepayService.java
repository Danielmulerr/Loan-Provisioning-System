package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface LoanRepayService {
    ResponseEntity<GenericResponse> repayLoan(Long loanId, double repaymentAmount);

    ResponseEntity<GenericResponse> repaymentHistory(Long loanId);

    ResponseEntity<GenericResponse> repaymentHistory();
}
