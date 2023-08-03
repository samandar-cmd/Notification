package smart.botexample.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.model.enums.MessageStatus;
import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.Repository.TelegramMessagesRepository;
import smart.botexample.Repository.TelegramSubscriptionRepository;
import smart.botexample.Service.BotService;
import smart.botexample.model.enums.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by Samandarbek O'rmonov 25.07.۲۰۲۳
 **/
@Component
@RequiredArgsConstructor
public class SchedulerMessages {
    private final TelegramMessagesRepository messagesRepository;
    private final TelegramSubscriptionRepository telegramSubscriptionRepository;
    private final BotService botService;

    @Scheduled(cron = "*/1 * * * * *")
    public void cronJobSch() throws TelegramApiException {
        List<TelegramMessages> allByStatus = messagesRepository.findAllByStatusIs(MessageStatus.CREATED);
        if (allByStatus != null) {
            for (TelegramMessages byStatus : allByStatus) {
                Optional<TelegramSubscription> allByPhone = telegramSubscriptionRepository.findByPhone(byStatus.getPhone());
                if (allByPhone.isPresent() && allByPhone.get().getStatus().equals(UserStatus.STARTED)) {
                    Boolean aBoolean = botService.sendMessages(byStatus.getMessage(), byStatus.getPhone());
                    if (aBoolean) {
                        byStatus.setStatus(MessageStatus.SUBMITTED);
                        messagesRepository.save(byStatus);
                    }
                } else {
                    byStatus.setStatus(MessageStatus.WAITING_SUBSCRIPTION);
                    messagesRepository.save(byStatus);
                }
            }
        }
    }
}

