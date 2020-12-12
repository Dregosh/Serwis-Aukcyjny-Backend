package com.sda.serwisaukcyjnybackend.domain.message;

import com.sda.serwisaukcyjnybackend.config.app.converters.MapConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;

@NoArgsConstructor
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = MapConverter.class)
    private HashMap<String, Object> payload;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;
    @Column(name = "send_tries")
    private Integer sendTries;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String email;

    public Message(HashMap<String, Object> payload, MessageType messageType, String email) {
        this.payload = payload;
        this.messageType = messageType;
        this.email = email;
        this.sendTries = 0;
        this.messageStatus = MessageStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public void addSendTry() {
        sendTries++;
    }
}
