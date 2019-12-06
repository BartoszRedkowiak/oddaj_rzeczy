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
    public ModelAndView passwordRecoveryMailGeneration(@RequestParam("email") String email,
                                                 ModelAndView modelAndView) {

        modelAndView.setViewName("status-message");
        modelAndView.addObject("statusMessage", "Na podany adres wysłano email z linkiem do zmiany hasła");
        boolean emailTest = ControllerValidator.validateEmail(modelAndView, email);
        if (!emailTest){
            modelAndView.setViewName("email-form");
            return modelAndView;
        }
        //No user under given email action
        User user = userService.findByEmail(email);
        if (user == null) {
            return modelAndView;
        }

        Token code = tokenService.generateToken(user, (byte) 2);

        try {
            emailService.sendPasswordResetCode(email, code);
        } catch (MailException e) {
            modelAndView.addObject("errorMessage", "Wystąpił problem podczas próby dostarczenia wiadomości");
            modelAndView.setViewName("email-form");
            return modelAndView;
        }
        return modelAndView;
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
