package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.models.ChangePasswordDto;
import tdtu.ems.main.utils.BaseResponse;
import tdtu.ems.main.models.LoginDto;

@Controller
@RequestMapping("/")
public class AuthController {
    private WebClient.Builder _webClient;

    public AuthController(WebClient.Builder _webClient) {
        this._webClient = _webClient;
    }

    @PostMapping("auth/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginDto input) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/auth/token")
                    .bodyValue(input)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("auth/user")
    public ResponseEntity<BaseResponse> getUser(@RequestParam String email, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/auth/user?email=" + email)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/auth/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody ChangePasswordDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/auth/change-password")
                    .bodyValue(input)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
