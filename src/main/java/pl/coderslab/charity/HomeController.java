package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.CategoryDao;
import pl.coderslab.charity.repository.DonationDao;
import pl.coderslab.charity.repository.InstitutionDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    private final InstitutionDao institutionDao;
    private final DonationDao donationDao;
    private final CategoryDao categoryDao;

    public HomeController(InstitutionDao institutionDao, DonationDao donationDao, CategoryDao categoryDao) {
        this.institutionDao = institutionDao;
        this.donationDao = donationDao;
        this.categoryDao = categoryDao;
    }

    @RequestMapping("/")
    public String homeAction(Model model){
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
    public String form(Model model){
        List<Institution> institutions = institutionDao.findAll();
        model.addAttribute("institutions",institutions);
        List<Category> categories = categoryDao.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("donation",new Donation());
        return "form";
    }
    @PostMapping("/form")
    public String formPost(Donation donation){
        donationDao.save(donation);
        return "form";
    }
}
