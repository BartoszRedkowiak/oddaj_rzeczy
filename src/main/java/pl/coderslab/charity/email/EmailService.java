package pl.coderslab.charity.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.verification.Token;
import pl.coderslab.charity.verification.TokenRepository;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TokenRepository codeRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TokenRepository codeRepository) {
        this.javaMailSender = javaMailSender;
        this.codeRepository = codeRepository;
    }

    public void sendContactRequestEmail(ContactInformation information) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(information.getEmail());
        mail.setTo("oddajrzeczymailservice@gmail.com");
        mail.setSubject("Nowa wiadomość od: " + information.getName() + " " + information.getSurname());
        mail.setText(information.getMessage());

        javaMailSender.send(mail);
    }

    public void sendPasswordResetCode(String email, Token code) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("oddajrzeczymailservice@gmail.com");
        mail.setTo(email);
        mail.setSubject("Reset hasła do konta na portalu Oddaj Rzeczy");
        mail.setText("W celu zresetowania hasła kliknij w link: \n" + "http://localhost:8080/password-reset?token=" + code.getToken());

        javaMailSender.send(mail);
    }


}
