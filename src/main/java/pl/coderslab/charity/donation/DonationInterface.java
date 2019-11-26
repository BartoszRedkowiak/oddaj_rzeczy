package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DonationInterface extends JpaRepository<Donation, Long> {

    @Query("select sum(d.quantity) from Donation d")
    Long totalQuantity();

    @Query("select count(distinct d.institution) from Donation d")
    Long countDonatedInstitutions();
}
