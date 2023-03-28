package com.starskrime.chatgpt_telegram_bot.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CustomBotCommand {
    START("/start"),
    MYKEY("/mykey"),
    MODELIST("/modelist"),
    HELP("/help");

    public final String value;

}
