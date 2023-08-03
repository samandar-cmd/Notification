package smart.botexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import smart.botexample.Config.SchedulerMessages;
import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.Service.MessageService;
import smart.botexample.model.payload.UserRequest;

import java.util.List;

/**
 * Created by Samandarbek O'rmonov 24.07.۲۰۲۳
 **/
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ControllerMessages {
    private final MessageService messageService;

    @PostMapping("/information")
    public String setUser(@RequestBody UserRequest request) {
        return messageService.setUser(request);
    }
}

