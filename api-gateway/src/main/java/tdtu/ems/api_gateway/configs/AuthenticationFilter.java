package tdtu.ems.api_gateway.configs;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.utils.Logger;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator _routeValidator;
    private final JwtValidator _jwtValidator;
    private final Logger<AuthenticationFilter> _logger;

    public AuthenticationFilter(RouteValidator routeValidator, JwtValidator jwtValidator) {
        super(Config.class);
        _routeValidator = routeValidator;
        _jwtValidator = jwtValidator;
        _logger = new Logger<>(AuthenticationFilter.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (_routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization header is missing");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    Claims claims = _jwtValidator.validateToken(authHeader);
                }
                catch (Exception e) {
                    _logger.Error("GatewayFilter", e.getMessage());
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
