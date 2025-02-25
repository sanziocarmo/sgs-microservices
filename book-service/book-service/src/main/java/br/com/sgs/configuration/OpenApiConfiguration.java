package br.com.sgs.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
        info = @Info(
                title = "SGS API",
                version = "v1",
                description = "Documentation of Book Service - SGS API",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                )
        )
)
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("SGS API")
                        .version("v1")
                        .description("Documentation of Book Service - SGS API")
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
}
