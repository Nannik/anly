package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByName(String name);
    User findByEmail(String email);
    User findById(int id);
}
