package com.example.loanprovisioning.controller;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.service.RepayLoanService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/loans")
public class RepayLoanController {

    private final RepayLoanService loanService;

    public RepayLoanController(RepayLoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/{loanId}/repayLoan")
    ResponseEntity<GenericResponse> repayLoan(@NotNull(message = "Loan Id can not be Null")
                                              @PathVariable("loanId") final Long loanId) {
        return loanService.repayLoan(loanId);
    }
}
