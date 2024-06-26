package com.example.loanprovisioning.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MoneyTransferException extends CustomeException {
    private int status;
    private long dailyWithdrawal;
    private long dailyLimit;
    private long requestedAmount;


    public MoneyTransferException(String message, long dailyWithdrawal, long dailyLimit, long requestedAmount) {
        super(message);
        this.status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        this.dailyWithdrawal = dailyWithdrawal;
        this.dailyLimit = dailyLimit;
        this.requestedAmount = requestedAmount;
    }


    public MoneyTransferException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }

    public MoneyTransferException(String message) {
        super(message);
        this.status = HttpStatus.UNPROCESSABLE_ENTITY.value();
    }
}
