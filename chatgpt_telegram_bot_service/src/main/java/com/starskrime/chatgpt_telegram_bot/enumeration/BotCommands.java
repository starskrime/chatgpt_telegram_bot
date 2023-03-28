package com.starskrime.chatgpt_telegram_bot.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotCommands {
    START("/start"),
    MYKEY("/mykey"),
    MODELIST("/modelist"),
    HELP("/help");

    public String value;

}
