package jeevankumar.spring_boot_security.service;

import jeevankumar.spring_boot_security.dto.SignupRequest;
import jeevankumar.spring_boot_security.entity.Role;
import jeevankumar.spring_boot_security.entity.User;
import jeevankumar.spring_boot_security.repository.RoleRepository;
import jeevankumar.spring_boot_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(SignupRequest signupRequest){
        // check the if user exist or not
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            throw  new RuntimeException("Error: username has already taken!");
        }
        // check the email
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            throw new RuntimeException("Error: email has already exists");
        }
        // create new user
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null){
            Role userRole = roleRepository.findByName(Role.RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Role.RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(Role.RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Role.RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });

        }
        user.setRoles(roles);
        return userRepository.save(user);

    }
}
