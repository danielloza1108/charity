package pl.coderslab.charity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.interfaces.UserService;
import pl.coderslab.charity.repository.CategoryDao;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.InstitutionDao;
import pl.coderslab.charity.repository.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {
    private final InstitutionDao institutionDao;
    private final DonationDao donationDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final UserService userService;

    public HomeController(InstitutionDao institutionDao, DonationDao donationDao, CategoryDao categoryDao, UserDao userDao, UserService userService) {
        this.institutionDao = institutionDao;
        this.donationDao = donationDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String homeAction(Model model, Principal principal){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        List<Institution> institutions = institutionDao.findAll();
        model.addAttribute("institution",institutions);
        Optional<Integer> numberOfDonations  = donationDao.counter();
        if(!numberOfDonations.isPresent()){
            model.addAttribute("donationsCount",0);
            model.addAttribute("donations",0);
        }else {
            Optional<Integer> countOfDonations = donationDao.countOfDonations();
            model.addAttribute("donations", countOfDonations);
            model.addAttribute("donationsCount", numberOfDonations);
        }

        return "index";
    }

    @GetMapping("/form")
    public String form(Model model, Principal principal){
        List<Institution> institutions = institutionDao.findAll();
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        model.addAttribute("institutions",institutions);
        List<Category> categories = categoryDao.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("donation",new Donation());
        return "form";
    }
    @PostMapping("/form")
    public String formPost(Donation donation){
        donationDao.save(donation);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(User user){
        userService.saveUser(user);
        return "redirect:";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
