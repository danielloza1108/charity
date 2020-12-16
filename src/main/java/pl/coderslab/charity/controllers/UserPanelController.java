package pl.coderslab.charity.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.classes.PasswordChange;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserDao;

import java.security.Principal;
import java.security.SecureRandom;

@RequestMapping("/user")
@Controller
public class UserPanelController {
    private final UserDao userDao;

    public UserPanelController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/panel")
    public String panel(Principal principal, Model model){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        return "userPanel";
    }
    @GetMapping("/edit")
    public String edit(Principal principal, Model model){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
            model.addAttribute("user", user);
        }
        return "userEdit";
    }
    @PostMapping("/edit")
    public String editSave(Principal principal,User userToSave){
        User user = userDao.findByEmail(principal.getName());
        user.setName(userToSave.getName());
        user.setSurname(userToSave.getSurname());
        user.setEmail(userToSave.getEmail());
        userDao.save(user);
        return "redirect:/user/panel";
    }

    @GetMapping("/changePassword")
    public String changePassword(Principal principal, Model model){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
            model.addAttribute("password", new PasswordChange());
        }
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePass(PasswordChange passwordChange,Principal principal){
        User user = userDao.findByEmail(principal.getName());
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        if(!bCryptPasswordEncoder.matches(passwordChange.getPassword(),user.getPassword())){
            return "redirect:/user/changePassword";
        }
        if(!passwordChange.getNewPassword().equals(passwordChange.getReNewPassword())){
            return "redirect:/user/changePassword";
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordChange.getNewPassword()));
        userDao.save(user);
        return "redirect:/user/panel";
    }
}
