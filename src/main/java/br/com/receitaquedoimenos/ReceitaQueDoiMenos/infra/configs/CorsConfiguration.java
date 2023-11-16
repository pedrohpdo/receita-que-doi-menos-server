package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração para lidar com Cross-Origin Resource Sharing (CORS) na aplicação.
 */
@Configuration
public class CorsConfiguration {

    /**
     * Configuração CORS para permitir solicitações de origens específicas e métodos HTTP.
     *
     * @return Um {@link WebMvcConfigurer} com a configuração CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigs() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(
                        "*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

}
