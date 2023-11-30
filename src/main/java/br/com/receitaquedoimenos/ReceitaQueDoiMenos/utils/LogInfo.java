package br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogInfo {
    public static String RECIPE_CREATED(String userIdentificator, String recipeName) {
        return String.format("TOPIC: RECEITA CRIADA | USER: %s | MESSAGE: RECEITA %s CRIADA", userIdentificator, recipeName);
    }

    public static String RECIPE_DELETED(String userIdentificator, String recipeName) {
        return String.format("TOPIC: RECEITA EXCLUIDA | USER: %s |  MESSAGE: RECEITA %s EXCLUIDA", userIdentificator, recipeName);
    }

    public static String FORBIDDEN_WORD(String word) {
        return String.format("TOPIC: PALAVRA PROIBIDA |  MESSAGE: PALAVRA ENCONTRADA { %s }", word);
    }

    public static String TOKEN_GENERATED(String userIdentificator) {
        return String.format("TOPIC: TOKEN | USER: %s |  MESSAGE: TOKEN DE ACESSO GERADO",userIdentificator);
    }

    public static String AUTHENTICATION_PERMITTED(String userIdentificator) {
        return String.format("TOPIC: TOKEN | USER: %s |  MESSAGE: USUÁRIO AUTENTICADO", userIdentificator);
    }

    public static String VALIDATE_REFRESH_TOKEN(String userIdentificator) {
        return String.format("TOPIC: TOKEN | USER: %s |  MESSAGE: USUÁRIO AUTENTICADO + TOKEN DE ACESSO GERADO",userIdentificator);
    }
}
