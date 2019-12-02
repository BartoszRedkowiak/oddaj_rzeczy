package pl.coderslab.charity.verification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationUUIDCodeRepository extends JpaRepository<VerificationUUIDCode, Long> {
}
