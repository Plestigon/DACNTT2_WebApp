package da2.webapp.demo.controllers;

import da2.webapp.demo.models.LoginDto;
import da2.webapp.demo.models.User;
import da2.webapp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
//        ModelAndView model = new ModelAndView();
//        model.addObject("login_dto", new LoginDto());
//        model.setViewName("Login");
        model.addAttribute("login_dto", new LoginDto());
        return "Login";
    }

    @PostMapping("/login")
    public String loginSubmit(Model model, @ModelAttribute("login_dto") LoginDto data)
    {
        model.addAttribute("login_dto", data);
        if (data == null) {
            model.addAttribute("message", "ERROR: Data is null");
            return "Login";
        }
        User user = userService.getUserByEmail(data.getEmail());
        if (user != null) {
            if (Objects.equals(user.getPassword(), data.getPassword())) {
                model.addAttribute("message", "Logged in!");
                return "Login";
            }
            model.addAttribute("message", "Wrong password!");
            return "Login";
        }
        model.addAttribute("message", "Wrong username!");
        return "Login";
    }
}
