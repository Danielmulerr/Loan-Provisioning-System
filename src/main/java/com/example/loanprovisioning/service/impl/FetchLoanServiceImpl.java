package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.service.FetchLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;
import static java.util.Collections.emptyList;

@Service
@Slf4j
public class FetchLoanServiceImpl implements FetchLoanService {

    @Override
    public ResponseEntity<GenericResponse> getAllLoans(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully submitted Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while submitting loan. ", "Try again later", e);
            throw new GeneralException("Failed while submitting loan. Try again later");
        }
    }

    @Override
    public ResponseEntity<GenericResponse> getLoanById(Long loanId) {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully fetched Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while fetching loan. ", "Try again later", e);
            throw new GeneralException("Failed while fetching loan. Try again later");
        }
    }
}
