package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Usage  implements Serializable {
    int prompt_tokens;
    int completion_tokens;
    int total_tokens;
}
