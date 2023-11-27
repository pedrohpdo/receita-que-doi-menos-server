package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.*;
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

/**
 * Classe para lidar com exceções em nível de controlador, fornecendo respostas padronizadas.
 */
@RestControllerAdvice
@Slf4j(topic = "CONTROLLER_ADVICE")
public class HandlerControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Manipula exceções de validação de argumentos do método.
     *
     * @param exception A exceção de validação de argumentos do método.
     * @param headers Cabeçalhos HTTP da resposta.
     * @param status O código de status HTTP da resposta.
     * @param webRequest A solicitação da web.
     * @return Uma resposta padronizada para exceções de validação de argumentos do método.
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest webRequest) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Invalid Arguments");

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            response.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("DADOS INVÁLIDOS: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Manipula exceções de dados não encontrados.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de dados não encontrados.
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(DataNotFoundException exception,
                                                          HttpServletRequest request) {

        ErrorResponse result = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());

        log.error("DADOS NÃO ENCONTRADOS: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    /**
     * Manipula exceções para conflito de infomações.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de conflito de dados .
     */
    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            ConflictDataException exception,
            WebRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );

        log.error("CONFLITO DE DADOS: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Manipula exceções de dados já encontrados.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de dados já encontrados.
     */
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Object> handlerEntityAlreadyExistsException(
            DataAlreadyExistsException exception,
            WebRequest request
    ) {
        log.error("DADOS JÁ EXISTENTES: " + exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Manipula exceções de operações não autorizadas.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de operações não autorizadas.
     */
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<Object> handlerUnauthorizedOperationException(
            UnauthorizedOperationException exception,
            WebRequest request
    ) {
        log.error("OPERAÇÃO NÃO AUTORIZADA: " + exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Manipula exceções de palavras proibidas encontradas.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de palavras proibidas encontradas.
     */
    @ExceptionHandler(ForbbidenWordException.class)
    public ResponseEntity<Object> handlerForbbidenWordException(
            ForbbidenWordException exception,
            WebRequest request
    ) {
        log.error("PALAVRA PROIBIDA ENCONTRADA: " + exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Manipula exceções com relação a problemas na geração ou validação de tokens.
     *
     * @param exception A exceção de dados não encontrados.
     * @param request O HttpServletRequest associado à exceção.
     * @return Uma resposta padronizada para exceções de geração ou validação de tokens.
     */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handlerTokenException(TokenException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        log.error("PROBLEMAS COM TOKEN: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
