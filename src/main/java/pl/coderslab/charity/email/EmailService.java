package pl.coderslab.charity.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.verification.Token;

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

    public void sendCode(String email, Token token, String url) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("oddajrzeczymailservice@gmail.com");
        mail.setTo(email);

        if (token.getCodeType() == 1){
            mail.setSubject("Link do aktywacji konta na portalu Oddaj Rzeczy");
            mail.setText("W celu aktywacji konta kliknij w link: \n" + url + "/account-activation?token=" + token.getToken());
        } else {
            mail.setSubject("Reset hasła do konta na portalu Oddaj Rzeczy");
            mail.setText("W celu zresetowania hasła kliknij w link: \n" + url + "/password-reset?token=" + token.getToken());
        }

        javaMailSender.send(mail);
    }

}
