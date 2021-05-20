package mofa.wangzhe.reactive.sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author LD
 */

@Configuration
@EnableWebFluxSecurity
public class MyWebFluxSecurityConfig {

    /**
     * CSRF
     *
     * @param http ServerHttpSecurity
     * @return SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
//                缓存
                .headers(headers -> headers
                        .cache(ServerHttpSecurity.HeaderSpec.CacheSpec::disable)
                )
//                注销地址
                .logout(logout -> logout.requiresLogout(new PathPatternParserServerWebExchangeMatcher("/logout")))
//                csrf令牌
                .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
//                登录
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                ).httpBasic(withDefaults()).oauth2Login(withDefaults());
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

}
