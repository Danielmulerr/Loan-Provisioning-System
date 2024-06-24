package com.example.loanprovisioning.config.feign;

import com.example.loanprovisioning.config.AppConstants;
import com.example.loanprovisioning.dto.MockRequestDto;
import com.example.loanprovisioning.dto.MockResponseDto;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

@FeignClient(name = "mock-service-client", url = "${mock.service.url}", configuration = FeignClientConfig.class)
public interface FeignServiceClient {
    @PostMapping(value = "/invoke", consumes = "application/json")
    ResponseEntity<MockResponseDto> pspServicePay(@RequestBody MockRequestDto pspPaymentRequestDto);
}

class FeignClientConfig {

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
    public Logger.Level feignLoggerLevel() {
        //NONE - No logging (DEFAULT).
        //BASIC - Log only the request method and URL and the response status code and execution time.
        //HEADERS - Log the basic information along with request and response headers.
        //FULL - Log the headers, body, and metadata for both requests and responses.
        return Logger.Level.BASIC;
    }
}