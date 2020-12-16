package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.entity.User;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "login";
    }
}
