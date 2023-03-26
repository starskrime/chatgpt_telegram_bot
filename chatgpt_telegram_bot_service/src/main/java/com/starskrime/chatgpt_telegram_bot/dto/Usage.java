package com.starskrime.chatgpt_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage  implements Serializable {
    int prompt_tokens;
    int completion_tokens;
    int total_tokens;
}
