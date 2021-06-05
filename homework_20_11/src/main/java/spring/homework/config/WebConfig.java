package spring.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.resource.VersionResourceResolver;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> htmlRouter(
            @Value("classpath:/templates/index.html") final Resource indexHtml,
            @Value("classpath:/templates/table.js") final Resource tableJS,
            @Value("classpath:/templates/actions.js") final Resource actionsJS
    ) {
        return route()
                .GET("/", serverRequest -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml))
                .GET("/table", serverRequest -> ok().contentType(new MediaType("application","javascript")).bodyValue(tableJS))
                .GET("/actions", serverRequest -> ok().contentType(new MediaType("application","javascript")).bodyValue(actionsJS))
                .build();
    }
}
