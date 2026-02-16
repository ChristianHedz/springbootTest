package mx.com.asteci.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final InfoConfig infoConfig;
    private final ContactConfig contactConfig;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title(infoConfig.getTitle())
                .version(infoConfig.getVersion())
                .description(infoConfig.getDescription())
                .contact(new Contact()
                        .name(contactConfig.getName())
                        .email(contactConfig.getEmail())
                        .url(contactConfig.getUrl()));
    }
}
