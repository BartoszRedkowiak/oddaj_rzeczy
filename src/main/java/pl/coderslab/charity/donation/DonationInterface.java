package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationInterface extends JpaRepository<Donation, Long> {
}
