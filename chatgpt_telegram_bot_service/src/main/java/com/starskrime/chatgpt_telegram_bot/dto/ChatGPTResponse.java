package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Data;
import org.jboss.jandex.*;


import java.awt.*;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ChatGPTResponse implements Serializable {
    private String id;
    private String object;
    private String model;
    private LocalDate created;
    private List<Choice> choices;
    //private TypeTarget.Usage usage;
}
