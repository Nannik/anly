package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.message.ParentMessage;

import java.util.List;

@Repository
public interface ParentMessageRepo extends CrudRepository<ParentMessage, Long> {
    ParentMessage findById(int id);
    List<ParentMessage> findAllByProductId(int productId);
    List<ParentMessage> findAllByUserId(int id);
}
