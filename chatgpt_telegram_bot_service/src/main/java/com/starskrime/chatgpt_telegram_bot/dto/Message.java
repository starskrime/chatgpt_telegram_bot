package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    String content;
    String role;
}
