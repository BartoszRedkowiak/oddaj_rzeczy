package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "{quantity.notNull}")
    @Min(value = 1, message = "{quantity.min}")
    @Max(value = 200, message = "{quantity.max}")
    Integer quantity;

    @NotEmpty(message = "{categories.notEmpty}")
    @ManyToMany
    @JoinTable(name="donations_categories",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories;

    @NotNull(message = "{institution.notNull}")
    @ManyToOne
    Institution institution;

    @Size(min = 3, max = 30, message = "{sizeRange}")
    String street;

    @Size(min = 3, max = 30, message = "{sizeRange}")
    String city;

    @Pattern(regexp = "\\d{2}-\\d{3}", message = "{zipCode.pattern}")
    String zipCode;

    @NotNull(message = "{pickUpDate.notNull}")
    @Future(message = "{pickUpDate.future}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate pickUpDate;

    @NotNull(message = "{pickUpTime.notNull}")
    LocalTime pickUpTime;

    @Size(max = 255, message = "{sizeMax}")
    String pickUpComment;

}
