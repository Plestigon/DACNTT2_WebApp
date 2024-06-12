package tdtu.ems.api_gateway.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tdtu.ems.api_gateway.utils.BaseResponse;
import tdtu.ems.api_gateway.utils.Logger;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator _routeValidator;
    private final JwtUtils _jwtUtils;
    private final Logger<AuthenticationFilter> _logger;
    private final ObjectMapper _objectMapper;

    public AuthenticationFilter(RouteValidator routeValidator, JwtUtils jwtUtils, ObjectMapper objectMapper) {
        super(Config.class);
        _routeValidator = routeValidator;
        _jwtUtils = jwtUtils;
        _objectMapper = objectMapper;
        _logger = new Logger<>(AuthenticationFilter.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (_routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return unauthenticated(exchange.getResponse());
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = "";
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                try {
                    Claims claims = _jwtUtils.validateToken(token);
                    String role = claims.get("role", String.class);
                    int a = 0;
                }
                catch (Exception e) {
                    _logger.Error("GatewayFilter", e.getMessage());
                    return invalidToken(exchange.getResponse(), e.getMessage());
                }
            }
            return chain.filter(exchange);
        }));
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        String body = "Error";
        try {
            body = _objectMapper.writeValueAsString(new BaseResponse(null, 401, "Unauthenticated"));
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        catch (Exception e) {
            _logger.Error("unauthenticated", e.getMessage());
        }
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    Mono<Void> invalidToken(ServerHttpResponse response, String msg) {
        String body = "Error";
        try {
            body = _objectMapper.writeValueAsString(new BaseResponse(null, 401, msg));
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        catch (Exception e) {
            _logger.Error("invalidToken", e.getMessage());
        }
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    public static class Config {

    }
}
