package pl.coderslab.charity.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendContactRequestEmail(ContactInformation information) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(information.getEmail());
        mail.setTo("oddajrzeczymailservice@gmail.com");
        mail.setSubject("Nowa wiadomość od: " + information.getName() + " " + information.getSurname());
        mail.setText(information.getMessage());

        javaMailSender.send(mail);
    }


}
