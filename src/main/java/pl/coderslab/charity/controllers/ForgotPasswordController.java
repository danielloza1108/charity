package pl.coderslab.charity.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import pl.coderslab.charity.classes.EmailService;
import pl.coderslab.charity.classes.PasswordChange;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserDao;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    private final UserDao userDao;
    private final ITemplateEngine iTemplateEngine;
    private final EmailService emailService;

    public ForgotPasswordController(UserDao userDao, ITemplateEngine iTemplateEngine, EmailService emailService) {
        this.userDao = userDao;
        this.iTemplateEngine = iTemplateEngine;
        this.emailService = emailService;
    }

    @GetMapping("/forgot")
    public String forgot(Principal principal, Model model){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        model.addAttribute("user",new User());
        return "forgotPassword";
    }

    @PostMapping("/forgot")
    public String sendEmail(User user){
        User byEmail = userDao.findByEmail(user.getEmail());
        Context context = new Context();
        context.setVariable("header", "Odzyskiwanie hasła");
        context.setVariable("title", "Odzyskiwanie hasła");
        context.setVariable("description", "Aby ustawić nowe hasło kliknij w ten link");
        context.setVariable("userEmail","http://localhost:8080/newPassword/"+byEmail.getHash());
        String body = iTemplateEngine.process("template.html", context);
        emailService.prepareAndSend(user.getEmail(), "Wiadomość", body);
        return "redirect:/login";
    }

    @GetMapping("/newPassword/{hash}")
    public String setNewPassword(@PathVariable String hash, Model model){
        model.addAttribute("password",new PasswordChange());
        return "newPassword";
    }
    @PostMapping("/newPassword/{hash}")
    public String setNew(PasswordChange passwordChange, @PathVariable String hash){
        User byHash = userDao.findByHash(hash);
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        if(!passwordChange.getNewPassword().equals(passwordChange.getReNewPassword())){
            return "redirect:/newPassword/{hash}";
        }
        byHash.setPassword(bCryptPasswordEncoder.encode(passwordChange.getNewPassword()));
        userDao.save(byHash);
        return "redirect:/login";
    }
}
