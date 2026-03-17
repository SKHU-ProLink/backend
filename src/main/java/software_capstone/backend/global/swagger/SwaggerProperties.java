package software_capstone.backend.global.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "swagger")
public record SwaggerProperties(List<String> servers) {
}
