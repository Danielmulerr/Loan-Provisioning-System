package com.example.loanprovisioning.controller;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.service.FetchLoanService;
import jakarta.validation.constraints.NotNull;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.loanprovisioning.config.AppConstants.*;
import static com.example.loanprovisioning.utils.PageableUtils.getPageable;

@RestController
@RequestMapping("api/v1/loans")
public class FetchLoanController {

    private final FetchLoanService loanService;

    public FetchLoanController(FetchLoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    ResponseEntity<GenericResponse> getAllLoans(
            @RequestParam(name = "fromDate", required = false) LocalDateTime fromDate,
            @RequestParam(name = "toDate", required = false) LocalDateTime toDate,
            @RequestParam(value = "sortType", defaultValue = LAST_UPDATE_DATE_DESC, required = false) List<String> sortType,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        val pageable = getPageable(sortType, pageNumber, pageSize);
        return loanService.getAllLoans(fromDate, toDate, pageable);
    }

    @GetMapping("/{loanId}")
    ResponseEntity<GenericResponse> getSingleLoan(@NotNull(message = "Loan Id can not be Null")
                                                  @PathVariable("loanId") final Long loanId) {
        return loanService.getLoanById(loanId);
    }

}
