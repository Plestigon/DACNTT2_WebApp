package tdtu_ems.main.controllers;

//import tdtu_ems.main.models.SecurityUser;
import tdtu_ems.main.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@EnableMethodSecurity
//@EnableMethodSecurity
@Controller
@RequestMapping(path="/")
public class HomeController {
    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String index(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) auth.getAuthorities();
//        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(authorities);
//        String role = roles.get(0).toString();
//        if (!role.equals("ROLE_ANONYMOUS")) {
//            SecurityUser u = (SecurityUser) auth.getPrincipal();
//            model.addAttribute("email", u.getUsername());
//            model.addAttribute("name", u.getName());
//            model.addAttribute("role", u.getRole());
//            return switch (u.getRole()) {
//                case "MANAGER" -> "Manager_page";
//                case "EMPLOYEE" -> "Employee_page";
//                default -> "redirect:/error";
//            };
//        }
        return "Landing_page";
    }

//    @PreAuthorize("hasRole('MANAGER')")
//    @GetMapping("/finances")
//    public String indexManager() {
//        return "Finances";
//    }
//
//    @PreAuthorize("hasRole('EMPLOYEE')")
//    @GetMapping("/complaints")
//    public String indexEmployee() {
//        return "Complaints_Form";
//    }

    @GetMapping("/profile")
    public String profile(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        SecurityUser u = (SecurityUser) auth.getPrincipal();
//        model.addAttribute("email", u.getUsername());
//        model.addAttribute("name", u.getName());
//        model.addAttribute("role", u.getRole());
        return "Profile_Page";
    }
}
