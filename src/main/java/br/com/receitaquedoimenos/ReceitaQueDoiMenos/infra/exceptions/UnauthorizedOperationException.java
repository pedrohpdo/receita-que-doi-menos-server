package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
