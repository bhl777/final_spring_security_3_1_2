package spb.alex.security_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spb.alex.security_3_1_2.model.Role;
import spb.alex.security_3_1_2.model.User;
import spb.alex.security_3_1_2.service.RoleService;
import spb.alex.security_3_1_2.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String listUsers(Model model, Authentication authentication) {

        if (authentication != null) {
            UserDetails ud = (UserDetails) authentication.getPrincipal();
            model.addAttribute("currUser", userService.findByName(ud.getUsername()));
        }
        else {
            model.addAttribute("currUser", new User());
        }

        model.addAttribute("newUser", new User());

        model.addAttribute("users", userService.findAllUsers());

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "admin"; // имя представления;

    }

    @GetMapping("/new")
    public String getAddPage(Model model) {

        model.addAttribute("user", new User());

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUserWithRoles(user, user.getRoles());

        return "redirect:/admin/admin";
    }

    @GetMapping("/delete")
    public String getDeletePage(Model model,
                                @RequestParam Long id) {
        User user = userService.findById(id); // Загружаем пользователя по id
        model.addAttribute("user", user); // Передаем существующего пользователя

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);

        return "redirect:/admin/admin";
    }

    @GetMapping("/update")
    public String getUpdatePage(Model model,
                                @RequestParam Long id) {
        User user = userService.findById(id); // Загружаем пользователя по id
        model.addAttribute("user", user); // Передаем существующего пользователя

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam Long id) {
        userService.updateUser(id, user, user.getRoles());

        return "redirect:/admin/admin";
    }

    @GetMapping(value = "/user")
    public String getUserProfile(Model model,
                                 @RequestParam Long id) {

        model.addAttribute("user", userService.findById(id));

        return "user";
    }
}
