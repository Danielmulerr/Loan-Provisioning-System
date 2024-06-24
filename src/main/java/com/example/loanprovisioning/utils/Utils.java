package com.example.loanprovisioning.utils;

import com.example.loanprovisioning.dto.GenericResponse;
import com.example.loanprovisioning.dto.GenericResponsePageable;
import com.example.loanprovisioning.dto.PageableDto;
import com.example.loanprovisioning.exception.ResourceNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;


public class Utils {

    private Utils() {

    }

    public static String getRandomUuid() {
        return UUID.randomUUID().toString();
    }

    public static ResponseEntity<GenericResponse> prepareResponse(final HttpStatus status, final String message, final Object content) {
        return ResponseEntity.status(status)
                .body(new GenericResponse(status.value(), message, content));
    }

    public static ResponseEntity<GenericResponsePageable> prepareResponseWithPageable(final HttpStatus status, final String message, final Object content, PageableDto pageableDto) {
        return ResponseEntity.status(status)
                .body(new GenericResponsePageable<>(status.value(), message, content, pageableDto));
    }


    public static void checkResponseHasContent(Object object) {
        if (object instanceof Optional<?> e) {
            if (e.isEmpty()) {
                throw new ResourceNotFoundException("No resource found for current user");
            }
        } else if (ObjectUtils.isEmpty(object)) {
            throw new ResourceNotFoundException("No resource found for current user");
        }
    }
}
