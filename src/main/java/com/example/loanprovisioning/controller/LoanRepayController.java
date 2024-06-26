package com.example.loanprovisioning.controller;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.service.LoanRepayService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loans")
public class LoanRepayController {

    private final LoanRepayService loanService;

    public LoanRepayController(LoanRepayService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/{loanId}/repayLoan")
    ResponseEntity<GenericResponse> repayLoan(@NotNull(message = "Loan Id can not be Null")
                                              @PathVariable("loanId") final Long loanId,
                                              @NotNull
                                              @RequestParam("repaymentAmount") final Double repaymentAmount) {
        return loanService.repayLoan(loanId, repaymentAmount);
    }

    @GetMapping("/{loanId}/repayment/history")
    ResponseEntity<GenericResponse> repaymentHistory(@NotNull(message = "Loan Id can not be Null")
                                                     @PathVariable("loanId") final Long loanId) {
        return loanService.repaymentHistory(loanId);
    }

    @GetMapping("/repayment/history")
    ResponseEntity<GenericResponse> allRepaymentHistory() {
        return loanService.repaymentHistory();
    }
}
