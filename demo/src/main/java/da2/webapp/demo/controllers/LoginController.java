package da2.webapp.demo.controllers;

import da2.webapp.demo.models.LoginDto;
import da2.webapp.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    public LoginController() {}

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.addObject("login_dto", new LoginDto());
        model.setViewName("Login");
        return model;
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute("login_dto") LoginDto model)
    {
        LoginDto a = model;
        return "Login";
    }
}
