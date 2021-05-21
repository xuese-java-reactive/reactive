package mofa.wangzhe.reactive.sys.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * 身份在验证管理器
 *
 * @author LD
 */
public class AuthenticationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    /**
     * 权限
     *
     * @param mono                 身份
     * @param authorizationContext 上下文
     * @return
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        //从Redis中获取当前路径可访问角色列表
//        URIuri = authorizationContext.getExchange().getRequest().getURI();
//        Objectobj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
//        List<String> authorities = Convert.toList(String.class, obj);
//        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
////认证通过且角色匹配的用户可访问当前路径
//        returnmono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(newAuthorizationDecision(false));

        return Mono.just(new AuthorizationDecision(true));
    }
}
