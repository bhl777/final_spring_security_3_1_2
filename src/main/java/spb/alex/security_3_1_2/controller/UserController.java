package spb.alex.security_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spb.alex.security_3_1_2.model.User;
import spb.alex.security_3_1_2.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUserPage(Model model) {
        return "user";
    }

    @GetMapping(value = "/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());

        return "users"; // имя представления;
    }

    @GetMapping("/admin/new")
    public String getAddPage(@ModelAttribute("user") User user) {

        return "new";
    }

    @PostMapping("/admin/new")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("selectedIds") Long[] selectedIds,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors() || user.getPassword() == null || user.getPassword().isEmpty()) {
            // Верните пользователя на форму с сообщением об ошибке
            return "redirect:/new?error=Password cannot be empty";
        }

        userService.createUser(user,selectedIds);

        return "redirect:/admin/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);

        return "redirect:/users";
    }

}
