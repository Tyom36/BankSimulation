package rogue.tyom.sub.securityapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rogue.tyom.sub.securityapp.util.PersonCreateException;
import rogue.tyom.sub.securityapp.util.PersonErrorResponse;
import rogue.tyom.sub.securityapp.util.PersonLoginException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonLoginException.class)
    public ResponseEntity<PersonErrorResponse> handleException(PersonLoginException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonCreateException.class)
    public ResponseEntity<PersonErrorResponse> handleException(PersonCreateException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
