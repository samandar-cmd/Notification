package smart.botexample.model.Entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import smart.botexample.model.enums.MessageStatus;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Samandarbek O'rmonov 20.07.۲۰۲۳
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "telegram_messages")
@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class TelegramMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private UUID clientsId;
    private UUID subscriptionId;
    private String messageId;
    private String message;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.CREATED;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Map<String,Object> attributes = new HashMap<>();
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Map<String,Object> reason = new HashMap<>();
    private Integer state = 1;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}

