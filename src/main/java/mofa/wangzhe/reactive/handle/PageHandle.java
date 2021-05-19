package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**
 * @author LD
 */

@Slf4j
@Component
public class PageHandle {

    /**
     * 统一路径跳转
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> page(ServerRequest request) {
        String path = request.pathVariable("path");
        String page = request.pathVariable("page");
        Resource relative = new DefaultResourceLoader().getResource("classpath:/templates/" + path + "/" + page + ".html");
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(relative);
    }
}
