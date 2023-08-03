package smart.botexample.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.User;
import org.hibernate.annotations.CreationTimestamp;
import smart.botexample.model.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Samandarbek O'rmonov 24.07.۲۰۲۳
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "telegram_subscriptions_flow")
@Entity
public class TelegramSubscriptionFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private Long subscriptionId;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Integer state = 1;
    @CreationTimestamp
    private Date createdAt;
}

