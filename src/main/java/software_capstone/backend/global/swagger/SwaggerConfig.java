package software_capstone.backend.global.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_KEY = "bearerAuth";

    @Value("${APP_SERVER_URL}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server().url(serverUrl);

        OpenAPI openAPI = new OpenAPI()
                .servers(List.of(server))
                .components(new Components()
                        .addSecuritySchemes(BEARER_KEY,
                                new SecurityScheme()
                                        .name(BEARER_KEY)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(BEARER_KEY))
                .info(apiInfo());

        return openAPI;
    }

    private Info apiInfo() {
        return new Info()
                .title("SKHU-ProLink")
                .description("ProLink Swagger입니다.")
                .version("1.0.1");
    }
}
