package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;

@Controller
@ResponseBody
@RequestMapping("/")
public class FinanceController {
    private final WebClient.Builder _webClient;

    public FinanceController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("finance/associates")
    public ResponseEntity<BaseResponse> getAssociates(@RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/associates")
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
