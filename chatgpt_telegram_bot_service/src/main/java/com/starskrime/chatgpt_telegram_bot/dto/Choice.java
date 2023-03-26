package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Choice implements Serializable {
    Message message;
    String finish_reason;
    int index;
}
