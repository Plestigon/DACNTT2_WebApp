package da2.webapp.demo.controllers;

import da2.webapp.demo.models.LoginDto;
import da2.webapp.demo.models.User;
import da2.webapp.demo.repositories.UserRepository;
import da2.webapp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

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
