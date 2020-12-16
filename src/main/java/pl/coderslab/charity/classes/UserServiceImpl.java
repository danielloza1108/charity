package pl.coderslab.charity.classes;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.Role;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.interfaces.UserService;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserDao;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public User findByUserName(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUser(User appUser) {

    }

    @Override
    public void saveUser(User appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        appUser.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(appUser);
    }
}
