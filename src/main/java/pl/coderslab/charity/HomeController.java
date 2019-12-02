package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.email.ContactInformation;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;
    private final EmailService emailService;
    private final Validator validator;

    @Autowired
    public HomeController(InstitutionService institutionService, DonationService donationService, UserService userService, EmailService emailService, Validator validator) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.userService = userService;
        this.emailService = emailService;
        this.validator = validator;
    }

    @RequestMapping("/")
    public String homeAction(Model model){
        model.addAttribute("institutions", institutionService.getFirst10());
        model.addAttribute("donationsCount", donationService.getTotalQuantity());
        model.addAttribute("contributionCount", donationService.getDonatedInstCount());
        return "index";
    }

    @GetMapping("/login")
    public String loginAction(){
        return "login";
    }

    @GetMapping("/register")
    public String registerGetAction(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String add(@Valid User user,
                      BindingResult result,
                      HttpServletRequest request) {
        if (result.hasErrors()) { return "register"; }

        String secondPassInput = request.getParameter("password2");

        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null){
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
                                    @RequestHeader("Referer") String redirectUrl){
        if (result.hasErrors()){
            return "redirect:" + redirectUrl + "?contactSuccess=false";
        }
        emailService.sendContactRequestEmail(information);
        return "redirect:" + redirectUrl + "?contactSuccess=true";
    }

    @GetMapping("/forgot-password")
    public String passwordRecoveryGetAction(){
        return "emailForm";
    }

    @PostMapping()
    public String passwordRecoveryPostAction(@Valid @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
                                                         String email,
                                             BindingResult result){
        if(result.hasErrors()){
            return "redirect:/forgot-password?error=true";
        }


        return "redirect:/";
    }

    @ModelAttribute("contactInformation")
    public ContactInformation setModelContactInformation(){ return new ContactInformation();}

}
