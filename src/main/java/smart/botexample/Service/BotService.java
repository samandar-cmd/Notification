package smart.botexample.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import smart.botexample.Config.BotConfig;
import smart.botexample.Repository.TelegramSubscriptionFlowRepository;
import smart.botexample.model.Entity.TelegramMessages;
import smart.botexample.model.Entity.TelegramSubscription;
import smart.botexample.model.Entity.TelegramSubscriptionFlow;
import smart.botexample.model.enums.UserStatus;
import smart.botexample.Repository.TelegramMessagesRepository;
import smart.botexample.Repository.TelegramSubscriptionRepository;

import java.util.*;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
@Component
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {
    final private BotConfig botConfig = new BotConfig();
    final private TelegramSubscriptionRepository telegramSubscriptionRepository;
    final private TelegramSubscriptionFlowRepository telegramSubscriptionFlowRepository;

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            if ("/start".equals(text)) {
                TelegramSubscription telegramSubscription = new TelegramSubscription();
                TelegramSubscriptionFlow flow = new TelegramSubscriptionFlow();
                Optional<TelegramSubscription> byUserId = telegramSubscriptionRepository.findByUserId(update.getMessage().getChatId());
                if (byUserId.isPresent()) {
                    byUserId.get().setStatus(UserStatus.STARTED);
                    TelegramSubscription save = telegramSubscriptionRepository.save(byUserId.get());
                    flow.setStatus(UserStatus.STARTED);
                    flow.setSubscriptionId(save.getId());
                    telegramSubscriptionFlowRepository.save(flow);
                } else {
                    telegramSubscription.setUserId(update.getMessage().getChatId());
                    telegramSubscription.setStatus(UserStatus.STARTED);
                    TelegramSubscription save = telegramSubscriptionRepository.save(telegramSubscription);
                    flow.setStatus(UserStatus.STARTED);
                    flow.setSubscriptionId(save.getId());
                    telegramSubscriptionFlowRepository.save(flow);
                }
                try {
                    start(update.getMessage().getChatId(), update.getMessage().getChat().getFirstName());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().hasContact()) {
                SendMessage message = new SendMessage();
                TelegramSubscriptionFlow flow = new TelegramSubscriptionFlow();
                Contact contact = update.getMessage().getContact();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Telefon raqamingiz: " + contact.getPhoneNumber() + "\n" +
                        "Profil nomingiz:" + contact.getFirstName());
                Optional<TelegramSubscription> byUserId = telegramSubscriptionRepository.findByUserId(contact.getUserId());
                if (byUserId.isPresent()) {
                    byUserId.get().setPhone(contact.getPhoneNumber());
                    TelegramSubscription save = telegramSubscriptionRepository.save(byUserId.get());
                    flow.setPhone(contact.getPhoneNumber());
                    flow.setSubscriptionId(save.getId());
                    telegramSubscriptionFlowRepository.save(flow);
                }
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ("/stop".equals(text)) {
                SendMessage message = new SendMessage();
                TelegramSubscriptionFlow flow = new TelegramSubscriptionFlow();
                message.setChatId(update.getMessage().getChatId().toString());
                Optional<TelegramSubscription> byUserId = telegramSubscriptionRepository.findByUserId(update.getMessage().getChatId());

                if (byUserId.isPresent()) {
                    byUserId.get().setStatus(UserStatus.STOPPED);
                    TelegramSubscription save = telegramSubscriptionRepository.save(byUserId.get());
                    flow.setSubscriptionId(save.getId());
                    flow.setStatus(UserStatus.STOPPED);
                    telegramSubscriptionFlowRepository.save(flow);
                    message.setText("Hurmatli " + update.getMessage().getFrom().getFirstName() + " siz botni toxtatdingiz qaytadan ishga tushurish uchun startni bosing!");
                }
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage message = new SendMessage();
                message.setText("Bunday buyruqni amalga oshiraolmayman!!! \n Ming bor uzur!!!");
                message.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void start(Long chatId, String firstName) throws TelegramApiException {
        String message = "Assalomu alaykum " + firstName + " sizga qanday yordam kerak";
        sendPhoneNumberRequest(chatId.toString(), message);
    }

    public Boolean sendMessages(String message, String phone) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        Optional<TelegramSubscription> byPhone = telegramSubscriptionRepository.findByPhone(phone);
        byPhone.ifPresent(telegramSubscription -> sendMessage.setChatId(telegramSubscription.getUserId().toString()));
        sendMessage.setText(message);
        execute(sendMessage);
        return true;
    }

    private void sendPhoneNumberRequest(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
//        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
//        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton1.setText("\uD83D\uDCF1  telefon raqamni yuboring!!!  \uD83D\uDCF1");
        keyboardButton1.setRequestContact(true);
//        keyboardButton2.setText("Botni to'xtatish");
        row1.add(keyboardButton1);
//        row2.add(keyboardButton2);
        rows.add(row1);
//        rows.add(row2);

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

