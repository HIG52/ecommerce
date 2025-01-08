package kr.hhplus.be.server.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "이커머스 API",
                version = "1.0"
        )
)
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .addServersItem(new Server().url("/"))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("이커머스 API")
                        .description("이커머스 API Spec")
                        .version("v1.0.0")
                );
    }

}
