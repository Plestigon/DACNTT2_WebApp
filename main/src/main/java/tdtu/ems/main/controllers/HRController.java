package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.main.models.SelectOptionsResult;
import tdtu.ems.main.models.hr.FormSubmitDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HRController {
    private WebClient.Builder _webClient;

    public HRController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("hr/submit-form/types")
    @ResponseBody
    public ResponseEntity<List<SelectOptionsResult>> getFormTypes() {
        var types = Enums.FormType.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < types.length; i++) {
            res.add(new SelectOptionsResult(types[i].name, types[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("hr/submit-form")
    @ResponseBody
    public ResponseEntity<BaseResponse> submitForm(@RequestBody FormSubmitDto entry) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://hr-service/api/hr/form")
                    .bodyValue(entry)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("hr/forms/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getForms(@PathVariable int id) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://hr-service/api/hr/forms/" + id)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("hr/contracts/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getContracts(@PathVariable int id) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://hr-service/api/hr/contracts/" + id)
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
