package mofa.wangzhe.reactive;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 函数式全局异常处理
 * //类似@RestControllerAdvice
 *
 * @author LD
 */

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        if (error instanceof FileNotFoundException) {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("message", "资源未找到");
        } else {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("message", "资源未找到");
        }
        return map;
    }

}
