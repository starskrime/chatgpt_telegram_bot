package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class Usage  implements Serializable {
    int prompt_tokens;
    int completion_tokens;
    int total_tokens;
}
