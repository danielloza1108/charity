package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.classes.Message;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.InstitutionDao;
import pl.coderslab.charity.repository.UserDao;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {
    private final UserDao userDao;
    private final InstitutionDao institutionDao;
    private final DonationDao donationDao;

    public HomeController(UserDao userDao, InstitutionDao institutionDao, DonationDao donationDao) {
        this.userDao = userDao;
        this.institutionDao = institutionDao;
        this.donationDao = donationDao;
    }

    @RequestMapping("/")
    public String homeAction(Model model, Principal principal){
        model.addAttribute("message", new Message());
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
}
