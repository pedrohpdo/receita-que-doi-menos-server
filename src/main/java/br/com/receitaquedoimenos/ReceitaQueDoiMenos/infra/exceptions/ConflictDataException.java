package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class ConflictDataException extends RuntimeException {

    public ConflictDataException(String entityName, String field) {
        super(String.format("Entity %s data already exists. Field: %s", entityName, field));
    }

    public ConflictDataException(String entityName) {
        super(String.format("Entity %s data already exists", entityName));
    }
}

