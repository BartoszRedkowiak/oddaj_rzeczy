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

    public void create(User user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName(roleName);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email); }

    public Integer countByEmail(String email){ return userRepository.countAllByEmail(email); }

    public User findById(Long id){ return userRepository.findById(id).orElse(null); }

    public List<User> findAllBySpecificRole(String roleName){ return userRepository.findBySpecificRole(roleName); }

    public void deleteById(Long id){ userRepository.deleteById(id); }

    public void updateCredentials(User updatedData){
        User userToUpdate = findById(updatedData.getId());
        userToUpdate.setFirstName(updatedData.getFirstName());
        userToUpdate.setLastName(updatedData.getLastName());
        userToUpdate.setEmail(updatedData.getEmail());
        userRepository.save(userToUpdate);
    }

    public void updatePassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void toggleEnabled(Long id){
        User user = findById(id);
        if (user == null){
            return;
        }
        user.setEnabled(Math.abs(user.getEnabled() - 1));
        userRepository.save(user);
    }

    public User findByToken(String token) { return userRepository.findByToken(token); }

}
