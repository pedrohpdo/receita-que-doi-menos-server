package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
        private Instant instant;
        private Integer status;
        private String message;
        private List<ValidationError> errors;

@Getter
@Setter
private static class ValidationError {
    private final String FIELD;
    private final String ERROR;

    public ValidationError(String FIELD, String ERROR) {
        this.FIELD = FIELD;
        this.ERROR = ERROR;
    }

}

    public ErrorResponse(Integer status, String message) {
        this.instant = Instant.now();
        this.status = status;
        this.message = message;
    }

    public void addValidationError(String field, String error) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<ValidationError>();
        }
        this.errors.add(new ValidationError(field, error));
    }
}
