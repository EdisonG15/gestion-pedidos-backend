package com.portafolio.gestionpedidos.dto.common.exception;
import com.portafolio.gestionpedidos.dto.common.messages.MessageCode;
import com.portafolio.gestionpedidos.dto.common.response.StandardResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFound(ResourceNotFoundException ex) {
        StandardResponse response = new StandardResponse(
                MessageCode.NOT_FOUND.getMessage(),
                String.valueOf(MessageCode.NOT_FOUND.getCode()),
                Map.of("error", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardResponse> handleBadRequest(BadRequestException ex) {
        StandardResponse response = new StandardResponse(
                MessageCode.BAD_REQUEST.getMessage(),
                String.valueOf(MessageCode.BAD_REQUEST.getCode()),
                Map.of("error", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleGeneric(Exception ex) {
        StandardResponse response = new StandardResponse(
                MessageCode.INTERNAL_ERROR.getMessage(),
                String.valueOf(MessageCode.INTERNAL_ERROR.getCode()),
                Map.of("error", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        StandardResponse response = new StandardResponse(
                MessageCode.BAD_REQUEST.getMessage(),
                String.valueOf(MessageCode.BAD_REQUEST.getCode()),
                Map.of("error", "Error de integridad en base de datos: " + ex.getRootCause().getMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (oldValue, newValue) -> newValue // por si se repite un campo
                ));

        StandardResponse response = new StandardResponse(
                MessageCode.BAD_REQUEST.getMessage(),
                String.valueOf(MessageCode.BAD_REQUEST.getCode()),
                Map.of("validaciones", errors)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
