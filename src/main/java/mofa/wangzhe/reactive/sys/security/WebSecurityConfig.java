package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 过滤连
 * 参考网络从新实现security相关配置
 * 参考：https://github.com/ard333/spring-boot-webflux-jjwt/blob/master/src/main/java/com/ard333/springbootwebfluxjjwt/security/WebSecurityConfig.java
 * //WebSecurityConfigurerAdapter
 *
 * @author LD
 */

@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final MyReactiveAuthorizationManager myReactiveAuthorizationManager;

    @Autowired
    public WebSecurityConfig(AuthenticationManager authenticationManager,
                             SecurityContextRepository securityContextRepository,
                             MyReactiveAuthorizationManager myReactiveAuthorizationManager) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.myReactiveAuthorizationManager = myReactiveAuthorizationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
//              关闭普通表单之类的
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()

//                配置授权
                .authorizeExchange()

//                .anyExchange()
//                .access(myReactiveAuthorizationManager)

//                其它所有不进行验证
                .pathMatchers(
                        "/",
                        "/api/login/login",
                        "/webjars/**",
                        "/js/**",
                        "/page/**",
                        "/img/**",
                        "/fav**"
                ).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                需要验证的路径
                .anyExchange().authenticated()
                .and()

//                配置异常情况
                .exceptionHandling()
//              配置在通过身份验证的用户不拥有所需权限时的处理方式
                .accessDeniedHandler((serverWebExchange, e) -> {
                    log.error("超权限操作：", e);
                    return Mono.create(m -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(new ResultUtil2(false, "权限不足", null)));
                })
//                配置应用程序请求身份验证时的操作
                .authenticationEntryPoint((serverWebExchange, e) -> {
                    ServerHttpRequest request = serverWebExchange.getRequest();
                    log.error("身份验证未通过：{}", request.getPath(), e);
                    return Mono.create(m -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(new ResultUtil2(false, "身份验证未通过", null)));
                })
                .and()

//                替换默认的身份验证
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository);

        return http.build();
    }

}
