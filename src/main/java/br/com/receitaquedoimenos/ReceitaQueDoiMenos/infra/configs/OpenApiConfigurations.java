package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações personalizadas para o OpenAPI (Swagger).
 */
@Configuration
public class OpenApiConfigurations {

    /**
     * Configuração personalizada para o OpenAPI, adicionando esquema de segurança Bearer (JWT).
     *
     * @return Uma instância de {@link OpenAPI} com as configurações personalizadas.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .name("bearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

}
