package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String message) {
        super(message);
    }

}
