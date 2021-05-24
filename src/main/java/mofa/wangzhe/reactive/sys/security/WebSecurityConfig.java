package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 过滤连
 *
 * @author LD
 */
@Configuration
@Order(99)
@Slf4j
public class WebSecurityConfig {


    /**
     * security的鉴权排除的url列表
     */
    public static final String[] EXCLUDED_AUTH_PAGES = {
            "/api/login/login",
            "/favicon.ico",
            "/page/**",
            "/webjars/**",
            "/js/**",
            "/css/**",
            "/img/**",
            "**.woff2",
            "/"
    };

    /**
     * 只需要登录就可以操作的url
     */
    public static final String[] AUTHENTICATED_PAGES = {
            "/api/**"
    };

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {

        http.securityContextRepository(new NoOpServerSecurityContextAutoRepository())
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable()

//                options所有请求都直接放行
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .and()

//                不需要拦截验证的
                .authorizeExchange()
                .pathMatchers(EXCLUDED_AUTH_PAGES).permitAll()
                .and()

//                需要拦截验证的
                .authorizeExchange()
                .pathMatchers(AUTHENTICATED_PAGES).authenticated()
                .and()

//               未授权
                .exceptionHandling()
                .accessDeniedHandler(new RestfulAccessDeniedHandler())
                .and()

//                认证失败
                .exceptionHandling()
                .authenticationEntryPoint(new RestfulAuthenticationEntryPoint())
                .and()

                .authorizeExchange()
                .pathMatchers("/**").access(new AuthenticationManager())
                .anyExchange().authenticated();
        return http.build();
    }


}
