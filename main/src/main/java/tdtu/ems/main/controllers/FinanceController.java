package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import tdtu.ems.main.models.finance.AssociateDto;
import tdtu.ems.main.models.finance.ContactDto;
import tdtu.ems.main.utils.BaseResponse;

import java.util.List;
import java.util.stream.Collectors;

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
//        catch (WebClientResponseException w) {
//            if (w.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//                return new ResponseEntity<>(new BaseResponse(null, 401, w.getMessage()), HttpStatus.UNAUTHORIZED);
//            }
//            return new ResponseEntity<>(new BaseResponse(null, 500, w.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("finance/associates/{id}")
    public ResponseEntity<BaseResponse> getAssociateById(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/associates/" + id)
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

    @PostMapping("finance/associates")
    public ResponseEntity<BaseResponse> addAssociate(@RequestBody AssociateDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/finance/associates")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(input)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("finance/associates/{id}")
    public ResponseEntity<BaseResponse> removeAssociate(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().delete()
                    .uri("http://api-gateway/api/finance/associates/" + id)
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

    @GetMapping("finance/associates/{id}/deals")
    public ResponseEntity<BaseResponse> getDeals(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/associates/" + id + "/deals")
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

    @PutMapping("finance/associates/{id}/contacts")
    public ResponseEntity<BaseResponse> addContactToAssociate(@PathVariable int id, @RequestParam int contactId,
                                                              @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/finance/associates/" + id + "/contacts?contactId=" + contactId)
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

    @GetMapping("finance/deals/{id}")
    public ResponseEntity<BaseResponse> getDeal(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/deals/" + id)
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

    @GetMapping("finance/deals/{id}/stages")
    public ResponseEntity<BaseResponse> getDealStageDetails(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/deals/" + id + "/stages")
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

    @GetMapping("finance/contacts")
    public ResponseEntity<BaseResponse> getContacts(@RequestParam(required = false) List<Integer> ids, @RequestParam String token) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/contacts" + query)
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

    @PostMapping("finance/contacts")
    public ResponseEntity<BaseResponse> addContact(@RequestBody ContactDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/finance/contacts")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(input)
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
