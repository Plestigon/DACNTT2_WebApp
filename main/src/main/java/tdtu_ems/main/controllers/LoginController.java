package tdtu_ems.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
//    private final EmployeeService userService;
//
//    @Autowired
//    public LoginController(EmployeeService userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("login_dto", new LoginDto());
//        return "Login";
//    }
//
//    @PostMapping("/login")
//    public String loginSubmit(Model model, @ModelAttribute("login_dto") LoginDto data)
//    {
//        model.addAttribute("login_dto", data);
//        if (data == null) {
//            model.addAttribute("message", "ERROR: Data is null");
//            return "Login";
//        }
//        Employee user = userService.getUserByEmail(data.getEmail());
//        if (user != null) {
//            if (Objects.equals(user.getPassword(), data.getPassword())) {
//                model.addAttribute("message", "Logged in!");
//                model.addAttribute("logged_in_user", user);
//                return "redirect:/";
//            }
//            model.addAttribute("message", "Wrong password!");
//            return "Login";
//        }
//        model.addAttribute("message", "Wrong username!");
//        return "Login";
//    }

}
