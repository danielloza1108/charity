package pl.coderslab.charity.interfaces;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.entity.User;
@Component
public interface UserService {
    User findByUserName(String name);
    void updateUser(User appuser);
    void saveUser(User appUser);
}
