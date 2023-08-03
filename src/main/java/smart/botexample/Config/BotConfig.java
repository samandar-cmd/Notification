package smart.botexample.Config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
@Data
@Configuration
@PropertySource("application.properties")
public class BotConfig {
    public String getBotUsername() {
        return "giperdaho_bot";
    }

    public String getBotToken() {
        return "6338006010:AAHvwT7MnHNwSPco1hJVe6o6wecJLKbJIMs";
    }
}

