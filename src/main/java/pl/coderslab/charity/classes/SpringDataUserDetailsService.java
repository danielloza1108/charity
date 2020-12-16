package pl.coderslab.charity.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.interfaces.UserService;

import java.util.HashSet;
import java.util.Set;

public class SpringDataUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserRepository(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User appUser = userService.findByUserName(username);
        if(appUser.isEnabled() && appUser != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            appUser.getRoles().forEach(r ->
                    grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
            return new CurrentUser(appUser.getEmail(), appUser.getPassword(),
                    grantedAuthorities, appUser);
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}