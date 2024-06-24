package com.example.loanprovisioning.config.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GenericRestClient {
    private final RestTemplate restTemplate;


    public GenericRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T, R> ResponseEntity<R> sendRequest(String url, T payload, HttpMethod method, Class<R> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestEntity = new HttpEntity<>(payload, headers);
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }
}
