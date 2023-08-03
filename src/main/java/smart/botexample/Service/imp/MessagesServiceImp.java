package smart.botexample.Service.imp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.model.enums.MessageStatus;
import smart.botexample.model.enums.UserStatus;
import smart.botexample.Repository.TelegramMessagesRepository;
import smart.botexample.Repository.TelegramSubscriptionRepository;
import smart.botexample.Service.MessageService;
import smart.botexample.model.payload.UserRequest;

import java.util.List;

import static java.lang.Long.parseLong;

/**
 * Created by Samandarbek O'rmonov 24.07.۲۰۲۳
 **/
@Service
@RequiredArgsConstructor
public class MessagesServiceImp implements MessageService {
    private final TelegramMessagesRepository messagesRepository;
    private final TelegramSubscriptionRepository repository;

    public String setUser(UserRequest request) {
        TelegramMessages messageEntity = new TelegramMessages();
        messageEntity.setMessage(request.getMessage());
        messageEntity.setPhone(request.getPhone());
        messageEntity.setState(1);
        messagesRepository.save(messageEntity);
        return "success";
    }
}

