package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.Product;

import java.util.List;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findAllByCategoryId(int categoryId);

    Product findById(int id);
}
