package pl.coderslab.charity.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@Controller
public class FeedbackController {

    private final EmailConfiguration emailConfiguration;

    @Autowired
    public FeedbackController(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    @GetMapping("/feedback")
    public void sendFeedback(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String feedback){
        //Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        mailSender.setPort(this.emailConfiguration.getPort());
        mailSender.setUsername(this.emailConfiguration.getUsername());
        mailSender.setPassword(this.emailConfiguration.getPassword());

        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(email);
        mailMessage.setTo("rc@feedback.com");
        mailMessage.setSubject("New feedback from " + name);
        mailMessage.setText(feedback);

        //Send mail
        mailSender.send(mailMessage);

    }


}
