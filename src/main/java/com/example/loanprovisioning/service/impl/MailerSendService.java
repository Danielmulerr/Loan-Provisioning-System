package com.example.loanprovisioning.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class MailerSendService {

    @Value("${mailer-config.baseUrl}")
    private String url;
    @Value("${mailer-config.apiToken}")
    private String apiToken;
    private final RestTemplate restTemplate;

    private static final String MAILERSEND_API_URL = "https://api.mailersend.com/v1/email";

    public MailerSendService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void sendEmail(String from, String to, String subject, String text, String html) throws IOException {
        String json = """
                {
                    "from": {"email": "%s"},
                    "to": [{"email": "%s"}],
                    "subject": "%s",
                    "text": "%s",
                    "html": "%s"
                }
                """.formatted(from, to, subject, text, html);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
    }
}
