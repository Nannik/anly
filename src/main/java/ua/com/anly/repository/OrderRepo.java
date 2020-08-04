package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.Order;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {
    Order findById(int id);
}
