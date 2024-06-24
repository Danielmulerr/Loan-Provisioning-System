package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record GenericResponsePageable<T>(@JsonProperty("status") int status,
                                         @JsonProperty("message") String message,
                                         @JsonProperty("content") T content,
                                         @JsonProperty("pageable") PageableDto pageableDto) implements Serializable {
}
