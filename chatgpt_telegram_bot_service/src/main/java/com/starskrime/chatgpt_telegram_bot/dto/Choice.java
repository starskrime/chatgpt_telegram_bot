package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class Choice implements Serializable {
    Message message;
    String finish_reason;
    int index;
}
