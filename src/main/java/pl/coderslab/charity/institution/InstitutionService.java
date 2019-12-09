package pl.coderslab.charity.institution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getAll() {
        return institutionRepository.findAll();
    }

    public List<Institution> getFirst10() {
        return institutionRepository.findFirst10ByOrderByIdAsc();
    }

    public Institution findByName(String name) {
        return institutionRepository.findByName(name);
    }

    public Institution findById(Long id) {
        return institutionRepository.findById(id).orElse(null);
    }

    public void create(Institution institution) {
        institutionRepository.save(institution);
    }

    public void deleteById(Long id) {
        institutionRepository.deleteById(id);
    }

    public void update(Institution institution){
        institutionRepository.save(institution);
    }

}
