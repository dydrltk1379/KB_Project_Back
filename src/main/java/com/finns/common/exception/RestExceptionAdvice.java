package com.finns.common.exception;

import com.finns.member.exception.PasswordMissmatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestExceptionAdvice {

    @ExceptionHandler(PasswordMissmatchException.class)
    public ResponseEntity<?> handlePasswordError(Exception ex) {
        return ResponseEntity.status(400)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<?> handleNotFound(Exception ex) {
        return ResponseEntity.status(404)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<?> handleForbidden(Exception ex) {
        return ResponseEntity.status(403)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> handleUnauthorized(Exception ex) {
        return ResponseEntity.status(401)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(Exception ex) {
        return ResponseEntity.status(400)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(500)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }
}
