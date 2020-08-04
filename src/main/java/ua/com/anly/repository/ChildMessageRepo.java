package ua.com.anly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.anly.entity.message.ChildMessage;

import java.util.List;

@Repository
public interface ChildMessageRepo extends CrudRepository<ChildMessage, Long> {
    ChildMessage findById(int id);
    List<ChildMessage> findAllByParentMessageId(int parentMessageId);
}
