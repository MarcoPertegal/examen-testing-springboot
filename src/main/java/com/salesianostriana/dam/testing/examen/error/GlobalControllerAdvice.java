package com.salesianostriana.dam.testing.examen.error;


import com.salesianostriana.dam.testing.examen.exception.RepeatedValueException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler({EntityNotFoundException.class})
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Entidad no encontrada");
        problemDetail.setProperty("date", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({RepeatedValueException.class})
    public ProblemDetail handleRepeatedValue(RepeatedValueException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("date", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ErrorResponse handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return ErrorResponse.builder(ex, HttpStatus.UNAUTHORIZED, ex.getMessage())
                .header("WWW-Authenticate", "Bearer")
                .build();

    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        return ErrorResponse.builder(ex, HttpStatus.FORBIDDEN, ex.getMessage()).build();

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Error de validación");
        problemDetail.setProperty("validation-errors", ex.getAllErrors().stream().map(
                e -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("object-error", e.getObjectName());
                    m.put("message", e.getDefaultMessage());

                    if (e instanceof FieldError fe) {
                        m.put("field", fe.getField());
                        m.put("rejected-value", fe.getRejectedValue());
                    }
                    return m;
                }
                ).collect(Collectors.toList())
        );

        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(problemDetail);

    }
}
