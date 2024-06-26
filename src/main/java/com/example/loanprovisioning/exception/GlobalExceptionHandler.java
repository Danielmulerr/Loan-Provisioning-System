package com.example.loanprovisioning.exception;

import com.example.loanprovisioning.dto.ValidationErrorResponse;
import jakarta.persistence.PersistenceException;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String VALIDATION_ERROR_REQUEST_OBJECT = "Validation error occurred while processing request object.";
    public static final String ERROR_PROCESSING_REQUEST = "Error while processing request object. ";

    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<UnauthorizedResponse> handleAccessDeniedException(SQLTransientConnectionException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UnauthorizedResponse("Failed while connecting to datasource."));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<UnauthorizedResponse> handleAccessDeniedException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UnauthorizedResponse("Failed while connecting to datasource."));
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<UnauthorizedResponse> handleAccessDeniedException(PersistenceException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UnauthorizedResponse("Failed while connecting to datasource."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(ValidationErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(VALIDATION_ERROR_REQUEST_OBJECT).validationErrors(errors).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnexpectedTypeException.class})
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(UnexpectedTypeException ex) {
        log.error(LOG_PREFIX, "Error while preparing validation error response", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ValidationErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(VALIDATION_ERROR_REQUEST_OBJECT).build());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {
        log.error(LOG_PREFIX, ERROR_PROCESSING_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ValidationErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                        .message(ERROR_PROCESSING_REQUEST + ex.getMessage()).build());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<UnauthorizedResponse> resourceNotFoundException(ResourceNotFoundException unExpectedRoleException) {
        log.error(LOG_PREFIX, unExpectedRoleException.getMessage(), "");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new UnauthorizedResponse(unExpectedRoleException.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<BaseErrorResponseStatus> invalidInputException(InvalidInputException invalidInputException) {
        log.error(LOG_PREFIX, invalidInputException.getMessage(), "");
        return ResponseEntity.status(invalidInputException.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BaseErrorResponseStatus(invalidInputException.getStatus().value(), invalidInputException.getMessage()));
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseErrorResponseStatus> generalException(GeneralException generalException) {
        log.error(generalException.getMessage());
        return ResponseEntity.status(generalException.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BaseErrorResponseStatus(generalException.getStatus().value(), generalException.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseErrorResponse> handleDuplicateKeyException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseErrorResponse(HttpStatus.CONTINUE.value(), "Duplicate key violation: " + extractErrorMessage(ex.getLocalizedMessage())));
    }

    private String extractErrorMessage(String errorMessage) {
        int startIndex = errorMessage.indexOf("Key");
        int endIndex = errorMessage.indexOf("]", startIndex);
        return errorMessage.substring(startIndex, endIndex + 1).trim();
    }

//    @ExceptionHandler(SizeLimitExceededException.class)
//    public ResponseEntity<UnauthorizedResponse> generalException(SizeLimitExceededException sizeLimitExceededException) {
//        log.error(sizeLimitExceededException.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(new UnauthorizedResponse("Failed to upload file, maximum file limit exceeded, " + sizeLimitExceededException.getMessage()));
//    }


    @ExceptionHandler(MoneyTransferException.class)
    public ResponseEntity<BaseErrorResponse> apiKeyException(MoneyTransferException moneyTransaferException) {
        log.error(moneyTransaferException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(new BaseErrorResponse(moneyTransaferException.getStatus(), moneyTransaferException.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<BaseErrorResponse> handleServletException(MissingServletRequestParameterException ex) {
        log.error(LOG_PREFIX, "MissingServletRequestParameterException while validating request object", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class BaseErrorResponse {
        private int status;
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class BaseErrorResponseStatus {
        private int status;
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ValidationErrorResponseMessage {
        private int status;
        private String message;
        private List<ErrorReason> errors;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorReason {
        private String field;
        private String reason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class UnauthorizedResponse {
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ApiKeyException {
        private String message;
        private String apiKey;
        private Long companyId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class NotAChairpersonResponse {
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class NotActiveAccountResponse {
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ErrorResponse {
        private int status;
        private String cause;
        private String message;
        private String context;
    }

}
