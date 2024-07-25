package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.models.hr.ContractDto;
import tdtu.ems.main.utils.BaseResponse;
import tdtu.ems.main.utils.Enums;
import tdtu.ems.main.utils.SelectOptionsResult;
import tdtu.ems.main.models.hr.EmployeeDto;
import tdtu.ems.main.models.hr.FormSubmitDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/")
public class HRController {
    private final WebClient.Builder _webClient;

    public HRController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("hr/submit-form/types")
    public ResponseEntity<List<SelectOptionsResult>> getFormTypes() {
        var types = Enums.FormType.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < types.length; i++) {
            res.add(new SelectOptionsResult(types[i].name, types[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("hr/submit-form")
    public ResponseEntity<BaseResponse> submitForm(@RequestBody FormSubmitDto entry, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/hr/form")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    public ResponseEntity<BaseResponse> getForms(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/hr/forms/" + id)
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

    @GetMapping("hr/forms/{id}/approve")
    public ResponseEntity<BaseResponse> getFormsForApproval(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/hr/forms/" + id + "/approve")
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

    @PutMapping("hr/forms/{id}")
    public ResponseEntity<BaseResponse> approveForm(@PathVariable int id, @RequestParam boolean approve, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().put()
                    .uri("http://api-gateway/api/hr/forms/" + id + "?approve=" + approve)
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

    @GetMapping("hr/forms/approvers")
    public ResponseEntity<BaseResponse> getApprovers(@RequestParam int userId, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/employees/approvers?userId=" + userId)
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

    @GetMapping("hr/contracts/{id}")
    public ResponseEntity<BaseResponse> getContracts(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/hr/contracts/" + id)
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

    @GetMapping("hr/contracts/types")
    public ResponseEntity<List<SelectOptionsResult>> getContractTypes() {
        var types = Enums.ContractType.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < types.length; i++) {
            res.add(new SelectOptionsResult(types[i].name, types[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("hr/contracts")
    public ResponseEntity<BaseResponse> addContract(@RequestBody ContractDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/hr/contracts")
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

    @GetMapping("hr/employees")
    public ResponseEntity<BaseResponse> getEmployees(@RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/employees")
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

    @GetMapping("hr/employees/roles")
    public ResponseEntity<List<SelectOptionsResult>> getEmployeeRoles() {
        var types = Enums.Role.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < types.length; i++) {
            res.add(new SelectOptionsResult(types[i].name, types[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("hr/employees/departments")
    public ResponseEntity<BaseResponse> getDepartmentOptions() {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/employees/departments/options")
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("hr/employees")
    public ResponseEntity<BaseResponse> addEmployee(@RequestBody EmployeeDto input, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/employees")
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

    @DeleteMapping("hr/employees/{id}")
    public ResponseEntity<BaseResponse> removeEmployee(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().delete()
                    .uri("http://api-gateway/api/employees/" + id)
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

    @GetMapping("hr/summary/{id}")
    public ResponseEntity<BaseResponse> getSummary(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/hr/summary/" + id)
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

    @GetMapping("hr/employees/chart-data")
    public ResponseEntity<BaseResponse> getEmployeeRoleChartData(@RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/employees/chart-data")
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
