package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import pl.coderslab.charity.classes.EmailService;
import pl.coderslab.charity.classes.Message;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.CategoryDao;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.InstitutionDao;
import pl.coderslab.charity.repository.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class FormController {

    private final InstitutionDao institutionDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final DonationDao donationDao;
    private final EmailService emailService;
    private final ITemplateEngine iTemplateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public FormController(InstitutionDao institutionDao, UserDao userDao, CategoryDao categoryDao, DonationDao donationDao, EmailService emailService, ITemplateEngine iTemplateEngine) {
        this.institutionDao = institutionDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.donationDao = donationDao;
        this.emailService = emailService;
        this.iTemplateEngine = iTemplateEngine;
    }

    @GetMapping("/form")
    public String form(Model model, Principal principal) {
        List<Institution> institutions = institutionDao.findAll();
        if (principal != null) {
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        model.addAttribute("institutions", institutions);
        List<Category> categories = categoryDao.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("donation", new Donation());
        model.addAttribute("message", new Message());
        return "form";
    }

    @PostMapping("/form")
    public String formPost(Donation donation, Principal principal) {
        User user = userDao.findByEmail(principal.getName());
        donation.setUser(user);
        donationDao.save(donation);
        return "redirect:/formConfirm";
    }

    @GetMapping("/formConfirm")
    public String confirm(Principal principal) {
        User user = userDao.findByEmail(principal.getName());
        Context context = new Context();
        context.setVariable("header", "Charity Fundation");
        context.setVariable("title", "Podsumowanie Dotacji");
        Donation donation = donationDao.findByUser(user.getId());
        context.setVariable("description", new StringBuilder().append(donation.getQuantity()).append(" worków: "));
        context.setVariable("userEmail","http://localhost:8080/setRegister/"+user.getHash());
        String body = iTemplateEngine.process("template.html", context);
        emailService.prepareAndSend(user.getEmail(), "Podsumowanie", body);
        return "formConfirm";
    }

    @PostMapping("/message")
    public String message(HttpServletRequest request, Message message) {
        Context context = new Context();
        context.setVariable("header", message.getEmail());
        context.setVariable("title", message.getName() + " " + message.getSurname());
        context.setVariable("description", message.getMessage());
        String body = iTemplateEngine.process("template.html", context);
        emailService.prepareAndSend("fundationcharity.nezly@gmail.com", "Wiadomość", body);
        return "redirect:";
    }

}
