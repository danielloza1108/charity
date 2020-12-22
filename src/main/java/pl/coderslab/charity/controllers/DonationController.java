package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.UserDao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@Controller
public class DonationController {

    public final DonationDao donationDao;
    public final UserDao userDao;

    public DonationController(DonationDao donationDao, UserDao userDao) {
        this.donationDao = donationDao;
        this.userDao = userDao;
    }

    @GetMapping("/donations")
    public String donations(Model model, Principal principal){
        if(principal != null){
            User user = userDao.findByEmail(principal.getName());
            model.addAttribute("name", user.getName());
        }
        List<Donation> donationsByUserId = donationDao.findDonationsByUserId(userDao.findByEmail(principal.getName()).getId());
        List<Object> donationsName = new ArrayList<>();
        for (Donation donation : donationsByUserId) {
            ArrayList<String> strings = new ArrayList<>();
            for (Category category : donation.getCategories()) {
                strings.add(category.getName());
            }
            donationsName.add(strings);
        }
        model.addAttribute("donations",donationDao.findDonationsByUserId(userDao.findByEmail(principal.getName()).getId()));
        model.addAttribute("categoryList",donationsName);
        return "donations";
    }
}
