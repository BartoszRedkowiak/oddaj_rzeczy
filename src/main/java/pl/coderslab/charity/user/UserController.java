package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.email.ContactInformation;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_USER")
    @GetMapping("/settings")
    public String editGetAction(Model model,
                                @AuthenticationPrincipal CurrentUser customUser){
        model.addAttribute("user", customUser.getUser());
        return "user-settings";
    }

    @ModelAttribute("contactInformation")
    public ContactInformation setModelContactInformation(){ return new ContactInformation();}


}
