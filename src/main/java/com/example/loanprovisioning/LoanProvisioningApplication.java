package com.example.loanprovisioning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableCaching
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableTransactionManagement
@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class LoanProvisioningApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanProvisioningApplication.class, args);
    }

}
