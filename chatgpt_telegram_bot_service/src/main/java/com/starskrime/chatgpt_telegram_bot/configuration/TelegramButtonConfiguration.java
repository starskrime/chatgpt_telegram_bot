package com.starskrime.chatgpt_telegram_bot.configuration;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class TelegramButtonConfiguration {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton MODE_BUTTON = new InlineKeyboardButton("Mode");
    private static final InlineKeyboardButton SET_KEY_BUTTON = new InlineKeyboardButton("Set Api Key");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");


    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        SET_KEY_BUTTON.setCallbackData("/setKey");
        MODE_BUTTON.setCallbackData("/modeList");
        HELP_BUTTON.setCallbackData("/help");


        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, HELP_BUTTON,MODE_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
