package com.starskrime.chatgpt_telegram_bot.configuration;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class TelegramButtonConfiguration {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton MODE_BUTTON = new InlineKeyboardButton("Set Mode");
    private static final InlineKeyboardButton SET_KEY_BUTTON = new InlineKeyboardButton("Set Api Key");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static final List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Lets go"),
            new BotCommand("/mykey", "I want to use my own relation with ChatGPT"),
            new BotCommand("/modelist", "(NOT IMPLEMENTED YET) Lets see what we have ?"),
            new BotCommand("/help", "Help me")
    );

    public static final String HELP_TEXT = "This bot will connect you with ChatGpt latest api.\n" +
            "The following commands are available to you:\n\n" +
            "/start - start the bot\n" +
            "/mykey - use your own OpenAI API key\n" +
            "/help - help menu";
    public static final String MODE_LIST="List of available modes: \n/grammar \n/ai";

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        SET_KEY_BUTTON.setCallbackData("/mykey");
        MODE_BUTTON.setCallbackData("/modelist");
        HELP_BUTTON.setCallbackData("/help");


        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON,SET_KEY_BUTTON,MODE_BUTTON, HELP_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
