package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    @JsonProperty("sendTo")
    private String sendTo;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("body")
    private String body;
}
