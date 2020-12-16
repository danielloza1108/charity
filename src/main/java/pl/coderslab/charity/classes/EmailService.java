package pl.coderslab.charity.classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private  String fromEmail;
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    public EmailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }
    public void prepareAndSend(String recipients, String subject, String content)  {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(this.fromEmail);
            messageHelper.setTo(recipients);
            messageHelper.setSubject(subject);
            messageHelper.setText(content,true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
//    public void prepareAndSendMessage(String recipients, String subject, String content,String fromEmail)  {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom(fromEmail);
//            messageHelper.setTo(recipients);
//            messageHelper.setSubject(subject);
//            messageHelper.setText(content,true);
//        };
//        try {
//            mailSender.send(messagePreparator);
//        } catch (MailException e) {
//            // runtime exception; compiler will not force you to handle it
//        }
//    }
}