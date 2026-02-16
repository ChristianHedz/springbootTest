package mx.com.asteci.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "api.info.contact")
public class ContactConfig {

    private String name;
    private String email;
    private String url;
}

