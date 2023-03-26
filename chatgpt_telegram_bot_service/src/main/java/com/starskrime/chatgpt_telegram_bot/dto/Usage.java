package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Getter;

@Getter
public class Usage {
    int prompt_tokens;
    int completion_tokens;
    int total_tokens;
}
