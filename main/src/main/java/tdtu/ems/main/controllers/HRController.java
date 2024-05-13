package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.main.models.SelectOptionsResult;

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
}
