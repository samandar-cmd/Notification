package smart.botexample.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart.botexample.model.enums.MessageStatus;
import smart.botexample.model.Entity.TelegramMessages;

import java.util.List;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
@Repository
public interface TelegramMessagesRepository extends JpaRepository<TelegramMessages, Long> {
    List<TelegramMessages> findAllByStatusIs(MessageStatus status);
}
