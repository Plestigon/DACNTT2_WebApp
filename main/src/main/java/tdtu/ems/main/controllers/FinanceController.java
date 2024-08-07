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
import tdtu.ems.main.models.finance.DealDto;
import tdtu.ems.main.utils.BaseResponse;
import tdtu.ems.main.utils.PagedResponse;

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
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("finance/associates/paged")
    public ResponseEntity<PagedResponse> getAssociatesPaged(@RequestParam int page, @RequestParam String token) {
        try {
            PagedResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/associates/paged?page=" + page)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(PagedResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new PagedResponse(null, 500, e.getMessage(), 0, page, 10), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("finance/deals")
    public ResponseEntity<PagedResponse> getDeals(@RequestParam(required = false) Integer associate,
                                                 @RequestParam int page,
                                                 @RequestParam String token) {
        try {
            PagedResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/deals?page=" + page
                            + (associate != null ? "&associate=" + associate : ""))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(PagedResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new PagedResponse(null, 500, e.getMessage(), 0, page, 10), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping("finance/deals")
    public ResponseEntity<BaseResponse> addDeal(@RequestBody DealDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/finance/deals")
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

    @PutMapping("finance/deals")
    public ResponseEntity<BaseResponse> editDeal(@RequestBody DealDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/finance/deals")
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

    @PutMapping("finance/deals/{id}/notes")
    public ResponseEntity<BaseResponse> updateDealNotes(@PathVariable int id, @RequestParam String value, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/finance/deals/" + id + "/notes?value=" + value)
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

    @PutMapping("finance/deals/{id}/stage")
    public ResponseEntity<BaseResponse> updateDealStage(@PathVariable int id, @RequestParam int value, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/finance/deals/" + id + "/stage?value=" + value)
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

    @GetMapping("finance/contacts/paged")
    public ResponseEntity<PagedResponse> getContactsPaged(@RequestParam int page, @RequestParam String token) {
        try {
            PagedResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/finance/contacts/paged?page=" + page)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(PagedResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new PagedResponse(null, 500, e.getMessage(), 0, page, 10), HttpStatus.INTERNAL_SERVER_ERROR);
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
