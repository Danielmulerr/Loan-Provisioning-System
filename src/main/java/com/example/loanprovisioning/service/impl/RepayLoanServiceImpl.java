package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.exception.CustomeException;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.service.RepayLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;
import static com.example.loanprovisioning.utils.Utils.prepareResponse;
import static java.util.Collections.emptyList;

@Service
@Slf4j
public class RepayLoanServiceImpl implements RepayLoanService {

    @Override
    public ResponseEntity<GenericResponse> repayLoan(Long loanId) {
        try {
            return prepareResponse(HttpStatus.OK, "Successfully repay Loan", emptyList());
        } catch (CustomeException e) {
            throw e;
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while repaying loan. ", "Try again later", e);
            throw new GeneralException("Failed while repaying loan. Try again later");
        }
    }
}
