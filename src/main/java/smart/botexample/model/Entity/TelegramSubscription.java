package smart.botexample.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smart.botexample.model.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Samandarbek O'rmonov 24.07.۲۰۲۳
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "telegram_subscriptions")
@Entity
public class TelegramSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Integer state = 1;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}

