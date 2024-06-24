package com.example.loanprovisioning.controller;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.service.MangeLoanService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loans")
public class ManageLoanController {

    private final MangeLoanService loanService;

    public ManageLoanController(MangeLoanService loanService) {
        this.loanService = loanService;
    }

    @PutMapping("/{loanId}/approve")
    ResponseEntity<GenericResponse> approveLoan(@NotNull(message = "Loan Id can not be Null")
                                                @PathVariable("loanId") final Long loanId) {
        return loanService.approveLoan(loanId);
    }

    @PutMapping("/{loanId}/reject")
    ResponseEntity<GenericResponse> rejectLoan(@NotNull(message = "Loan Id can not be Null")
                                               @PathVariable("loanId") final Long loanId) {
        return loanService.rejectLoan(loanId);
    }

    @PostMapping("/{loanId}/disburse")
    ResponseEntity<GenericResponse> disburseLoan(@NotNull(message = "Loan Id can not be Null")
                                                 @PathVariable("loanId") final Long loanId) {
        return loanService.disburseLoan(loanId);
    }

}
