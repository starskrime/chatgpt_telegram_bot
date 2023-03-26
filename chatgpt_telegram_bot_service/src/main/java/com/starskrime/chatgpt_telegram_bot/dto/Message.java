package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Message implements Serializable {
    String content;
    String role;
}
