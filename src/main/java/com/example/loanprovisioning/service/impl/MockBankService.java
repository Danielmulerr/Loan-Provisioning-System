package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.config.feign.BankFeignClient;
import com.example.loanprovisioning.dto.MockBankTransferRequestDto;
import com.example.loanprovisioning.dto.PaymentStatus;
import com.example.loanprovisioning.dto.PspPaymentResponseDto;
import com.example.loanprovisioning.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;

@Service
@Slf4j
public class MockBankService {
    private final BankFeignClient bankFeignClient;

    public MockBankService(BankFeignClient bankFeignClient) {
        this.bankFeignClient = bankFeignClient;
    }

    PspPaymentResponseDto transferMoney(MockBankTransferRequestDto bankTransferRequestDto) {
        try {
            /**
             * BELOW FOR REAL CALL
             val response = bankFeignClient.transferMoney(bankTransferRequestDto);
             if (response.getStatusCode().is2xxSuccessful()) {
             return response.getBody();
             }
             throw new GeneralException(
             "Error encountered while bank transfer");
             **/
            return new PspPaymentResponseDto("Trx12345135",
                    PaymentStatus.SUCCESS,
                    "Successfully made transfers");
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Failed while transferring money", "", e);
            throw new GeneralException("Failed while transferring money");
        }
    }
}
