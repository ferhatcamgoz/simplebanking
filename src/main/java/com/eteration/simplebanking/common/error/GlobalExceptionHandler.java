package com.eteration.simplebanking.common.error;

import com.eteration.simplebanking.transaction.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericError> handleEntityNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(new GenericError("Entity Not Found: " + ex.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericError> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(new GenericError(ex.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<GenericError> handleManagerException(InsufficientBalanceException ex) {
        return new ResponseEntity<>(new GenericError(ex.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}
