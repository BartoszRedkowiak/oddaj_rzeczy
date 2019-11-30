package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email); }

    public Integer countByEmail(String email){ return userRepository.countAllByEmail(email); }

    public User findById(Long id){ return userRepository.findById(id).orElse(null); }

    public List<User> findAllBySpecificRole(String roleName){ return userRepository.findBySpecificRole(roleName); }

    public void deleteById(Long id){ userRepository.deleteById(id); }

    public void update(User user){
        User userToUpdate = findById(user.getId());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userRepository.save(userToUpdate);
    }

    public void toggleEnabled(Long id){
        User user = findById(id);
        if (user == null){
            return;
        }
        user.setEnabled(Math.abs(user.getEnabled() - 1));
        userRepository.save(user);
    }



}
