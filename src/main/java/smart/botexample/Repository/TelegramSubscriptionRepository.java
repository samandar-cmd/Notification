package smart.botexample.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.model.enums.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
public interface TelegramSubscriptionRepository extends JpaRepository<TelegramSubscription, Long> {
    Optional<TelegramSubscription> findByPhone(String phone);

    Optional<TelegramSubscription> findByUserId(Long userId);
}