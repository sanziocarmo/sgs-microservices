package br.com.sgs.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Foo-Bar Endpoint")
@RestController
@RequestMapping("/book-service")
public class FooBarController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(FooBarController.class);

    @Operation(summary = "Find a specific book by your Foo-bar and currency")
    @GetMapping("/foo-bar")
//    @Retry(name = "foo-bar", fallbackMethod = "fooBarFallback")
//    @CircuitBreaker(name = "default", fallbackMethod = "fooBarFallback")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String fooBar() {

        logger.info("Request received for foo-bar");

//        var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);

        return "Foo-Bar!";
//        return response.getBody();
    }

    public String fooBarFallback(Exception e) {
        return "Fallback response for foo-bar";
    }
}
