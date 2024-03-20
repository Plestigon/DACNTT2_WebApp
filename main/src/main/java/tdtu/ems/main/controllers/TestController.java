package tdtu.ems.main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.services.HomeService;

@RestController
public class TestController {
    private HomeService homeService;
    private final WebClient.Builder _webClient;

    public TestController(HomeService homeService, WebClient.Builder webClient) {
        this.homeService = homeService;
        _webClient = webClient;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        homeService.sendMessage(message);
        return ResponseEntity.ok("Sent!");
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        Object res = null;
        res = _webClient.build().get()
                .uri("http://operation-management-service/api/operations/projects")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        return ResponseEntity.ok(res);
    }
}
