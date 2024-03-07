package tdtu_ems.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/operations")
public class OperationsController {
    private final String OPERATIONS_PAGE_TITLE = "Operations Management";

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", OPERATIONS_PAGE_TITLE);
        return "Operations/Operations";
    }
}
