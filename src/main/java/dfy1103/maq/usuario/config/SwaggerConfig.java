package dfy1103.maq.usuario.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 Listado de usuarios.")
                        .version("1.0")
                        .description("Documentacion de la API para crear el sistema de usuarios."))
                .components(new Components().addSecuritySchemes("bearerAuth",securityScheme))
                .addSecurityItem(securityRequirement);

    }
}
