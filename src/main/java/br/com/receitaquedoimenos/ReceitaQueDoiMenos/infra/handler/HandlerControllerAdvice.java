package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ConflictDataException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataAlreadyExistsException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j(topic = "CONTROLLER_ADVICE")
public class HandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest webRequest) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Invalid Arguments");

        log.error("Invalid Data Params");

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            response.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(DataNotFoundException exception,
                                                          HttpServletRequest request) {

        ErrorResponse result = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());

        log.error("Cannot Find Entity", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            ConflictDataException exception,
            WebRequest webRequest) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );

        log.error("Dados conflituosos dentro do sistema", exception);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Object> handlerEntityAlreadyExistsException(
            DataAlreadyExistsException exception,
            WebRequest request
    ) {
        log.error("Entidade não foi encontrada no sistema" + exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
