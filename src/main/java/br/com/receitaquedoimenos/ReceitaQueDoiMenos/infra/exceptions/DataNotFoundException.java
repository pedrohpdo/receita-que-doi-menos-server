package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
