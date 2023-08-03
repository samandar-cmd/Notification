package smart.botexample.Service;

import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.model.payload.UserRequest;

import java.util.List;

/**
 * Created by Samandarbek O'rmonov 02.08.۲۰۲۳
 **/
public interface MessageService {
    String setUser(UserRequest request);
}
