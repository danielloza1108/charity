package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import pl.coderslab.charity.classes.EmailService;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.interfaces.UserService;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.UserDao;

import java.util.Random;

@Controller
public class RegisterController {

    private final UserService userService;
    private final UserDao userDao;
    private final DonationDao donationDao;
    private final ITemplateEngine iTemplateEngine;
    private final EmailService emailService;

    public RegisterController(UserService userService, UserDao userDao, DonationDao donationDao, ITemplateEngine iTemplateEngine, EmailService emailService) {
        this.userService = userService;
        this.userDao = userDao;
        this.donationDao = donationDao;
        this.iTemplateEngine = iTemplateEngine;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(User user){
        String hash = generate();
        while (null != userDao.findByHash(hash)){
            hash = generate();
        }
        user.setHash(hash);
        userService.saveUser(user);
        Context context = new Context();
        context.setVariable("header", "Charity Fundation");
        context.setVariable("title", "Potwierdzenie rejestracji");
        context.setVariable("description", "Kliknij tutaj, aby potwierdzić konto.");
        context.setVariable("userEmail","http://localhost:8080/setRegister/"+user.getHash());
        String body = iTemplateEngine.process("template.html", context);
        emailService.prepareAndSend(user.getEmail(), "Podsumowanie", body);
        return "redirect:/";
    }

    @GetMapping("/setRegister/{hash}")
    public String set(@PathVariable String hash){
        User user = userDao.findByHash(hash);
        user.setEnable(true);
        userDao.save(user);
        return "redirect:/";
    }

    public String generate() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

}
