package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions;

public class EmptyIngredientException extends RuntimeException {

    public EmptyIngredientException(String message) { super(message); }
}
