package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
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
                    .uri("http://employee-service/api/auth/login")
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
}
