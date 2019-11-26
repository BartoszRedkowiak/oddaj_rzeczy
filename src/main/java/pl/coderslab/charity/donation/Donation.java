package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;

import javax.persistence.*;
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

    Integer quantity;

    @ManyToMany
    @JoinTable(name="donations_categories",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToOne
    Institution institution;

    String street;
    String city;
    String zipCode;
    LocalDate pickupDate;
    LocalTime pickupTime;
    String pickUpComment;



}
