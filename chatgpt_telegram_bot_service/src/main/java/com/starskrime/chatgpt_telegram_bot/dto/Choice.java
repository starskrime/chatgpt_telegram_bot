package com.starskrime.chatgpt_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice implements Serializable {
    Message message;
    String finish_reason;
    int index;
}
