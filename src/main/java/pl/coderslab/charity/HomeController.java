package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.email.ContactInformation;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.UserService;
import pl.coderslab.charity.utils.ControllerValidator;
import pl.coderslab.charity.verification.Token;
import pl.coderslab.charity.verification.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.regex.Pattern;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;
    private final EmailService emailService;
    private final TokenService tokenService;

    @Autowired
    public HomeController(InstitutionService institutionService, DonationService donationService, UserService userService, EmailService emailService, TokenService tokenService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.userService = userService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @RequestMapping("/")
    public String homeAction(Model model) {
        model.addAttribute("institutions", institutionService.getFirst10());
        model.addAttribute("donationsCount", donationService.getTotalQuantity());
        model.addAttribute("contributionCount", donationService.getDonatedInstCount());
        return "index";
    }

    @GetMapping("/login")
    public String loginAction() {
        return "login";
    }

    @GetMapping("/register")
    public String registerGetAction(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String add(@Valid User user,
                      BindingResult result,
                      HttpServletRequest request) {
        if (result.hasErrors()) {
            return "register";
        }

        String secondPassInput = request.getParameter("password2");

        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            result.addError(new FieldError("user", "email",
                    "Nie można zarejestrować konta na podany adres email"));
            return "register";
        }
        if (!user.getPassword().equals(secondPassInput)) {
            result.addError(new FieldError("user", "password",
                    "Podane hasła nie są zgodne"));
            return "register";
        }

        userService.create(user, "ROLE_USER");
        return "redirect:/";
    }

    @PostMapping("/contact")
    public String contactMailAction(@Valid ContactInformation information,
                                    BindingResult result,
                                    @RequestHeader("Referer") String redirectUrl) {
        if (result.hasErrors()) {
            return "redirect:" + redirectUrl + "?contactSuccess=false";
        }
        emailService.sendContactRequestEmail(information);
        return "redirect:" + redirectUrl + "?contactSuccess=true";
    }

    @GetMapping("/forgot-password")
    public String passwordRecoveryFormAction() {
        return "email-form";
    }

    @PostMapping("/forgot-password")
    public String passwordRecoveryMailGeneration(@RequestParam("email") String email) {

        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (!Pattern.matches(emailRegex, email)) {
            return "redirect:/forgot-password?validPassword=false";
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return "email-confirmation";
        }

        Token code = tokenService.generateToken(user, (byte) 2);

        try {
            emailService.sendPasswordResetCode(email, code);
        } catch (MailException e) {
            return "redirect:/forgot-password?errorMsg=true";
        }
        return "email-confirmation";
    }

    @GetMapping("/password-reset")
    public ModelAndView setPasswordEditView(@RequestParam("token") String token,
                                           ModelAndView modelAndView){
        Token code = tokenService.findOneByTokenAndType(token, (byte) 2);
        modelAndView.setViewName("status-message");
        if (code == null){
            modelAndView.addObject("statusMessage", "Link do zresetowania hasła jest nieprawidłowy lub wygasł");
            return modelAndView;
        } else if (!LocalDateTime.now().isBefore(code.getCreated().plusMinutes(15))){
            modelAndView.addObject("statusMessage", "Link do zresetowania hasła jest nieprawidłowy lub wygasł");
            return modelAndView;
        } else {
            modelAndView.addObject("token", token);
        }
        modelAndView.setViewName("password-edit");
        return modelAndView;
    }

    @PostMapping("/password-reset")
    public ModelAndView editPasswordAction(ModelAndView modelAndView,
                                           @RequestParam("password1") String password1,
                                           @RequestParam("password2") String password2,
                                           @RequestParam("token") String token){
        modelAndView.setViewName("status-message");
        //Password validation
        boolean test = ControllerValidator.validatePasswords(modelAndView, password1, password2);
        if (!test){
            modelAndView.setViewName("password-edit");
            return modelAndView;
        }
        //User search
        User user = userService.findByToken(token);
        if (user == null){
            modelAndView.addObject("statusMessage", "Błąd podczas próby aktualizacji hasła");
            return modelAndView;
        }
        //User update and token removal
        userService.updatePassword(user, password1);
        tokenService.deleteToken(token);

        modelAndView.addObject("statusMessage", "Aktualizacja hasła zakończona sukcesem");
        return modelAndView;
    }

    @ModelAttribute("contactInformation")
    public ContactInformation setModelContactInformation() {
        return new ContactInformation();
    }

}
