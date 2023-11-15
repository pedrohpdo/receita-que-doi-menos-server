package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe para representar uma resposta de erro padronizada.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    /**
     * O timestamp da ocorrência do erro.
     */
    private Instant instant;

    /**
     * O código de status HTTP.
     */
    private Integer status;

    /**
     * A mensagem de erro.
     */
    private String message;

    /**
     * Lista de erros de validação, se aplicável.
     */
    private List<ValidationError> errors;

    /**
     * Classe interna para representar um erro de validação.
     */
    @Getter
    @Setter
    private static class ValidationError {

        /**
         * O campo associado ao erro de validação.
         */
        private final String FIELD;

        /**
         * A descrição do erro de validação.
         */
        private final String ERROR;

        /**
         * Construtor para um erro de validação.
         *
         * @param FIELD O campo associado ao erro de validação.
         * @param ERROR A descrição do erro de validação.
         */
        public ValidationError(String FIELD, String ERROR) {
            this.FIELD = FIELD;
            this.ERROR = ERROR;
        }

    }

    /**
     * Construtor da classe ErrorResponse.
     *
     * @param status  O código de status HTTP.
     * @param message A mensagem de erro.
     */
    public ErrorResponse(Integer status, String message) {
        this.instant = Instant.now();
        this.status = status;
        this.message = message;
    }

    /**
     * Adiciona um erro de validação à lista de erros.
     *
     * @param field O campo associado ao erro de validação.
     * @param error A descrição do erro de validação.
     */
    public void addValidationError(String field, String error) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationError(field, error));
    }
}
