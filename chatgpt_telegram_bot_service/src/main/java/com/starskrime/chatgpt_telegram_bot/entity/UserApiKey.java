package com.starskrime.chatgpt_telegram_bot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class UserApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String telegramUserId;
    private String chatGptApiKey;

}
