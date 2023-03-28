package com.starskrime.chatgpt_telegram_bot.entity;

import com.starskrime.chatgpt_telegram_bot.enumeration.BotMode;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "userId")
    private String telegramUserId;

    @Column(name = "key")
    private String chatGptApiKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private BotMode botMode;

}
