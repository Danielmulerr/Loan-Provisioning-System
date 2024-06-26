package com.example.loanprovisioning.config.feign;

import com.example.loanprovisioning.config.AppConstants;
import com.example.loanprovisioning.dto.MockBankTransferRequestDto;
import com.example.loanprovisioning.dto.PspPaymentResponseDto;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

@FeignClient(name = "mock-bank-client", url = "${mock.bank.url}", configuration = FeignClientConfig.class)
public interface BankFeignClient {
    @PostMapping(value = "/transfer", consumes = "application/json")
    ResponseEntity<PspPaymentResponseDto> transferMoney(@RequestBody MockBankTransferRequestDto mockBankTransferRequestDto);
}

class BankFeignConfig {

    @Value("${mock.bank.apiKey}")
    private String apiKey;

    @Bean
    public static Request.Options requestOptions(ConfigurableEnvironment env) {
        //default read timeout : 60
        //default connection timeout: 10
        return new Request.Options(AppConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS, AppConstants.READ_TIMEOUT, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 2000, 3);
    }

    @Bean
    public RequestInterceptor bankApiInterceptor() {
        return template -> template.header("Authorization", "Bearer " + apiKey);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        //NONE - No logging (DEFAULT).
        //BASIC - Log only the request method and URL and the response status code and execution time.
        //HEADERS - Log the basic information along with request and response headers.
        //FULL - Log the headers, body, and metadata for both requests and responses.
        return Logger.Level.BASIC;
    }
}