package spb.alex.security_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spb.alex.security_3_1_2.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUserProfile(Model model, Authentication authentication) {
        if (authentication != null) {
            UserDetails ud = (UserDetails) authentication.getPrincipal();
            model.addAttribute("user", userService.findByName(ud.getUsername()));
        }

        return "user";
    }
}
