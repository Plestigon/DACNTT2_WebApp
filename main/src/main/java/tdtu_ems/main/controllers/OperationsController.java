package tdtu_ems.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/operations")
public class OperationsController {
    @GetMapping("/index")
    public String index(Model model) {
        return "Operations/Operations";
    }
}
