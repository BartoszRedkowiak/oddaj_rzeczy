package pl.coderslab.charity.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.validation.Valid;

@Controller
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    @Autowired
    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
    }

    private void addDonationAttributes(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("institutions", institutionService.getAll());
    }

    @Secured("ROLE_USER")
    @GetMapping("/new")
    public String newDonationGetAction(Model model) {
        model.addAttribute("donation", new Donation());
        addDonationAttributes(model);
        return "form";
    }

    @PostMapping("/new")
    public String newDonationPostAction(@Valid @ModelAttribute Donation donation,
                                        BindingResult result,
                                        Model model) {
        if (result.hasErrors()) {
            addDonationAttributes(model);
            return "form";
        }

        donationService.createDonation(donation);
        return "form-confirmation";
    }


}
