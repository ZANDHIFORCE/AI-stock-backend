package Nemsi.AiStock.controller;

import Nemsi.AiStock.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(String userid, String password, String email, String nickname, 
                         String name, String birthdd, String gender) {
        userService.create(userid, password, email, nickname, name, birthdd, gender);
        return "redirect:/user/login";
    }
}
