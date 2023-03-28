package com.starskrime.chatgpt_telegram_bot.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotMode {
    GRAMMAR("grammar"),
    AI("ai");
    public final String value;
}
