package pl.coderslab.charity.user;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final InstitutionService institutionService;

    @Autowired
    public AdminController(UserService userService, InstitutionService institutionService) {
        this.userService = userService;
        this.institutionService = institutionService;
    }


    @GetMapping("/users")
    public String usersListGetAction(Model model) {
        List<User> users = userService.findAllBySpecificRole("ROLE_USER");
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable Long id,
                             @RequestHeader("Referer") String redirectUrl) {
        userService.deleteById(id);
        return "redirect:" + redirectUrl + "/?deleteSuccess=true";
    }

    @GetMapping("/edit/{id}")
    public String userEditGetMapping(@PathVariable Long id,
                                     Model model,
                                     @RequestHeader("Referer") String referer) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:../?userExist=false";
        }
        model.addAttribute("user", user);
        model.addAttribute("referer", referer);
        return "userEdit";
    }

    @PostMapping("/edit/{id}")
    public String userEditPostMapping(@Valid User user,
                                      BindingResult result,
                                      HttpServletRequest request) {
        if (result.hasErrors()) {
            return "userEdit";
        }
        //Check for constraint violation
        try{
            userService.update(user);
        } catch (DataIntegrityViolationException e){
            result.addError(new FieldError("user", "email", "Nie można przypisać do konta podanego adresu email"));
            return "userEdit";
        }

        String redirectUrl = request.getParameter("referer");
        return "redirect:" + redirectUrl + "/?editSuccess=true";
    }

    @GetMapping("/toggle/{id}")
    public String userToggleEnabled(@PathVariable Long id) {
        userService.toggleEnabled(id);
        return "redirect:../users";
    }

    @GetMapping("/admins")
    public String adminListGetAction(Model model) {
        List<User> admins = userService.findAllBySpecificRole("ROLE_ADMIN");
        model.addAttribute("user", new User());
        model.addAttribute("admins", admins);
        return "adminList";
    }

    @PostMapping("/newAdmin")
    public String registerAdminAction(@Valid User user,
                                      BindingResult result,
                                      HttpServletRequest request) {
        if (result.hasErrors()) {
            return "adminList";
        }

        String secondPassInput = request.getParameter("password2");

        if (!user.getPassword().equals(secondPassInput)) {
            result.addError(new FieldError("user", "password",
                    "Podane hasła nie są zgodne"));
            return "adminList";
        }

        try{
            userService.create(user, "ROLE_ADMIN");
        } catch (DataIntegrityViolationException e){
            result.addError(new FieldError("user", "email",
                    "Nie można zarejestrować konta na podany adres email"));
            return "adminList";
        }

        return "redirect:admins";
    }

    @GetMapping("/institutions")
    public String institutionListGetAction(Model model) {
        model.addAttribute("institution", new Institution());
        model.addAttribute("institutions", institutionService.getAll());
        return "institutionList";
    }

    @PostMapping("/newInstitution")
    public String registerInstitutionAction(@Valid Institution institution,
                                            BindingResult result) {
        if (result.hasErrors()) {
            return "institutionList";
        }

        try {
            institutionService.create(institution);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            result.addError(new FieldError("institution", "name", "Instytucja o podanej nazwie już istnieje"));
            return "institutionList";
        }
        return "redirect:institutions";
    }

    @GetMapping("/institutions/delete/{id}")
    public String deleteInstitutionAction(@PathVariable Long id) {
        institutionService.deleteById(id);
        return "redirect:../";
    }

    @GetMapping("/institutions/edit/{id}")
    public String editInstitutionGetAction(@PathVariable Long id,
                                           Model model) {
        Institution institution = institutionService.findById(id);

        if (institution == null) {
            return "redirect:../?institutionExist=false";
        }
        model.addAttribute("institution", institution);
        return "institutionEdit";
    }

    @PostMapping("/institutions/edit/{id}")
    public String editInstitutionPostAction(@Valid Institution institution,
                                            BindingResult result) {
        if (result.hasErrors()) {
            return "institutionEdit";
        }
        try{
            institutionService.update(institution);
        } catch (DataIntegrityViolationException e){
            result.addError(new FieldError("institution", "name", "Instytucja o podanej nazwie już istnieje"));
            return "institutionEdit";
        }
        return "redirect:../";
    }


}
