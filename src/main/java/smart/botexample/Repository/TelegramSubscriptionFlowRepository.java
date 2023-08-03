package smart.botexample.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.model.Entity.TelegramSubscriptionFlow;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
public interface TelegramSubscriptionFlowRepository extends JpaRepository<TelegramSubscriptionFlow,Long> {

}
