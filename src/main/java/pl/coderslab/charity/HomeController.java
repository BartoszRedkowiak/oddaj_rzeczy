package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;

    @Autowired
    public HomeController(InstitutionService institutionService, DonationService donationService, UserService userService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.userService = userService;
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

        User existingUser = userService.getByEmail(user.getEmail());
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

        userService.create(user);
        return "redirect:/";
    }

}
