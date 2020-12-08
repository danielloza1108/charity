package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.charity.entity.Donation;

import java.util.Optional;

public interface DonationDao extends JpaRepository<Donation,Long> {
    @Query(value = "SELECT SUM(quantity) FROM donation;" , nativeQuery=true)
    Optional<Integer> counter();
    @Query(value = "SELECT COUNT(quantity) FROM donation;" , nativeQuery=true)
    Optional<Integer> countOfDonations();
    @Query(value = "INSERT INTO donations_categories ('donation_id','categories_id') VALUES (:donation,:category);" , nativeQuery=true)
    void addCategory(@Param("donation") Long donationId, @Param("category") Long categoryId);
    @Query(value = "select count(id) from donation;", nativeQuery = true)
    int donationCount();
}
