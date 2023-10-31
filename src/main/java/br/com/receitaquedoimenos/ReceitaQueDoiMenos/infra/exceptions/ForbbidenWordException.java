package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class ForbbidenWordException extends RuntimeException {
    public ForbbidenWordException(String message) {
        super(message);
    }
}
