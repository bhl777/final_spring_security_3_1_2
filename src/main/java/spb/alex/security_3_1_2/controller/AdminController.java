package spb.alex.security_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getAdminProfile(Model model, Authentication authentication) {
        if (authentication != null) {
            UserDetails ud = (UserDetails) authentication.getPrincipal();
            model.addAttribute("user", userService.findByName(ud.getUsername()));
        }

        return "admin";
    }

    @GetMapping(value = "/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());

        return "users"; // имя представления;
    }

//    @GetMapping("/new")
//    public String getAddPage(@ModelAttribute("user") User user) {
//
//        return "new";
//    }

    @GetMapping("/new")
    public String getAddPage(Model model) {

        model.addAttribute("user", new User());

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "new";
    }

//    @PostMapping("/new")
//    public String addUser(@ModelAttribute("user") User user,
//                          @RequestParam("selectedIds") Long[] selectedIds,
//                          BindingResult bindingResult) {
//        if (bindingResult.hasErrors() || user.getPassword() == null || user.getPassword().isEmpty()) {
//
//            return "redirect:/new?error=Password cannot be empty";
//        }
//
//        userService.createUser(user, selectedIds);
//
//        return "redirect:/admin/users";
//    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUserWithRoles(user, user.getRoles());

        return "redirect:/admin/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);

        return "redirect:/admin/users";
    }

    //    @GetMapping("/update")
//    public String getUpdatePage(@ModelAttribute("user") User user,
//                                @RequestParam Long id) {
//
//
//        return "update";
//    }
    @GetMapping("/update")
    public String getUpdatePage(Model model) {
        model.addAttribute("user", new User());

        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "update";
    }

//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute("user") User user,
//                             @RequestParam Long id,
//                             @RequestParam("selectedIds") Long[] selectedIds) {
//
//        userService.updateUser(id, user, selectedIds);
//
//        return "redirect:/admin/users";
//    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam Long id) {
        userService.updateUser(id, user, user.getRoles());

        return "redirect:/admin/users";
    }

}
