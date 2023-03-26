package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Getter;

@Getter
public class Choice {
    Message message;
    String finish_reason;
    int index;
}
