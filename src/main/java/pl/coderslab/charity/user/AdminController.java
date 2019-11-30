package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionService;

import javax.validation.Valid;
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
    public String usersListGetAction(Model model){
        List<User> users = userService.findAllBySpecificRole("ROLE_USER");
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:../";
    }

    @GetMapping("/users/edit/{id}")
    public String userEditGetMapping(@PathVariable Long id,
                           Model model){
        User user = userService.findById(id);
        if (user == null){
            return "redirect:../?userExist=false";
        }
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/users/edit/{id}")
    public String userEditPostMapping(@Valid User user,
                                      BindingResult result){
        if (result.hasErrors()){
            return "userEdit";
        }
        //Check if new email is already in db
        User userCheck = userService.findByEmail(user.getEmail());
        if (userCheck != null && userCheck.getId() != user.getId()){
            result.addError(new FieldError("user", "email", "Nie można przypisać do konta podanego adresu email"));
            return "userEdit";
        }
        userService.update(user);

        return "redirect:../?editSuccess=true";
    }

    @GetMapping("/users/toggle/{id}")
    public String userToggleEnabled(@PathVariable Long id){
        userService.toggleEnabled(id);
        return "redirect:../";
    }


    @GetMapping("/admins")
    public String adminListGetAction(Model model){
        List<User> admins = userService.findAllBySpecificRole("ROLE_ADMIN");
        model.addAttribute("newUser", new User());
        model.addAttribute("admins", admins);
        return "adminList";
    }

    @GetMapping("/institutions")
    public String institutionListGetAction(Model model){
        model.addAttribute("newInstitution", new Institution());
        model.addAttribute("institutions", institutionService.getAll());
        return "institutionList";
    }





}
