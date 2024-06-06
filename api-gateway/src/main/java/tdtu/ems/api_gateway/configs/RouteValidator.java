package tdtu.ems.api_gateway.configs;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final String[] OPEN_ENDPOINTS = {
            "/eureka",
            "/api/auth/token",
            "/api/auth/validate",
            "/api/employees/departments/options"
    };

    public Predicate<ServerHttpRequest> isSecured =
            request -> Arrays.stream(OPEN_ENDPOINTS).noneMatch(uri -> request.getURI().getPath().contains(uri));
}
