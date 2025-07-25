package pe.gob.pj.eje.penal.ms_motor_reglas.infraestructure.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@Schema(hidden = true)
@OpenAPIDefinition(
        info = @Info(title = "API Motor de Reglas para JUSPRO", version = "1",
                contact = @Contact(name = "Equipo de Desarrollo", email = "desarrollo@gob.pe", url = "http://localhost:10104"),
                description = "API para la gestión del motor de reglas del sistema JUSPRO"))
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description("__")//
                                .bearerFormat("JWT")));
    }
}