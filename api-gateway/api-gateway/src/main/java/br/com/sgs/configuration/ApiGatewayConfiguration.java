package br.com.sgs.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        // Obter as definições de rotas
        var definitions = locator.getRouteDefinitions().collectList().block();

        // Criar um grupo de OpenAPI para cada serviço que tenha "-service" no nome
        return definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .map(routeDefinition -> {
                    String name = routeDefinition.getId();
                    // Criar o GroupedOpenApi para cada grupo de API
                    return GroupedOpenApi.builder()
                            .group(name) // Nome do grupo
                            .pathsToMatch("/" + name + "/**") // Caminhos correspondentes
                            .build();
                })
                .collect(Collectors.toList());
    }
}
