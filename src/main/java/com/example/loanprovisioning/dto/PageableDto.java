package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record PageableDto(
        @JsonProperty("totalPages") int totalPages,
        @JsonProperty("totalElements") long totalElements,
        @JsonProperty("numberOfElements") int numberOfElements,
        @JsonProperty("last") boolean last,
        @JsonProperty("first") boolean first,
        @JsonProperty("empty") boolean empty
) implements Serializable {
}