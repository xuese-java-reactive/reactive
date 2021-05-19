package mofa.wangzhe.reactive.router;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.handle.LoginHandle;
import mofa.wangzhe.reactive.handle.PageHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author LD
 */

@Slf4j
@Configuration
public class Routers implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> initRouterFunction(
            PageHandle pageHandle,
            LoginHandle loginHandle

    ) {
        return RouterFunctions.nest(
                RequestPredicates.path("/page"),
                RouterFunctions.route(
                        RequestPredicates.GET("/{path}/{page}"),
                        pageHandle::page
                )
        ).andNest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/login"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/login"),
                                loginHandle::login
                        )
                )
        );
    }
}
