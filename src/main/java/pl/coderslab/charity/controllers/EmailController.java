package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.thymeleaf.ITemplateEngine;
import pl.coderslab.charity.classes.EmailService;
@Controller
public class EmailController {
    private final ITemplateEngine iTemplateEngine;
    private final EmailService emailService;
    @Value("${spring.mail.username}")
    private  String fromEmail;
    public EmailController(ITemplateEngine iTemplateEngine, EmailService emailService) {
        this.iTemplateEngine = iTemplateEngine;
        this.emailService = emailService;
    }
}
