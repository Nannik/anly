package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.Category;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {
    Category findById(int id);
}
