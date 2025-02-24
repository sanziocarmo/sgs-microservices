package br.com.sgs.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/get")
                        .filters(f -> f
                                .addRequestHeader("Hello", "World")
                                .addRequestParameter("Hello", "World"))
                .uri("http://httpbin.org:80"))
                .route(r -> r.path("/cambio-service/**")
                        .uri("lb://cambio-service"))
                .route(r -> r.path("/book-service/**")
                        .uri("lb://book-service"))
                .build();
    }

}
