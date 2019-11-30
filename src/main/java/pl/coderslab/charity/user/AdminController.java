package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String usersListGetAction(Model model){
//        Role userRole = new Role();
//        userRole.setId(1);
//        userRole.setName("ROLE_USER");
        List<User> users = userService.findAllBySpecificRole("ROLE_USER");
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/admins")
    public String adminListGetAction(Model model){
        return "adminList";
    }

    @GetMapping("/institutions")
    public String institutionListGetAction(Model model){
        return "institutionList";
    }





}
