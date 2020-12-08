package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entity.Category;

public interface CategoryDao extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
