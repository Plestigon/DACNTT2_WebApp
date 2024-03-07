package tdtu.ems.main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdtu.ems.main.services.HomeService;

@RestController
public class TestController {
    private HomeService homeService;

    public TestController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        homeService.sendMessage(message);
        return ResponseEntity.ok("Sent!");
    }
}
