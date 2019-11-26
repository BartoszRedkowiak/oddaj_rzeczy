package pl.coderslab.charity.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DonationService {

    private final DonationInterface donationInterface;
    @Autowired
    public DonationService(DonationInterface donationInterface) {
        this.donationInterface = donationInterface;
    }

    public Long getTotalQuantity(){
        return donationInterface.totalQuantity();
    }

}
