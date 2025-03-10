package br.com.sgs.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {

        List<GroupedOpenApi> groupedOpenApis = new ArrayList<>();

        Mono<List<org.springframework.cloud.gateway.route.RouteDefinition>> routeDefinitionsMono = locator.getRouteDefinitions().collectList();

        routeDefinitionsMono.block().stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId();
                    GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                    groupedOpenApis.add(groupedOpenApi);
                });

        return groupedOpenApis;
    }
}