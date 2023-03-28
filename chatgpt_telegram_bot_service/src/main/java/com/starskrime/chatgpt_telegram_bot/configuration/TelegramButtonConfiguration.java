package com.starskrime.chatgpt_telegram_bot.configuration;

import com.starskrime.chatgpt_telegram_bot.enumeration.CustomBotCommand;
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
            new BotCommand(CustomBotCommand.START.value, "Lets go"),
            new BotCommand(CustomBotCommand.MYKEY.value, "I want to use my own relation with ChatGPT"),
            new BotCommand(CustomBotCommand.MODELIST.value, "(NOT IMPLEMENTED YET) Lets see what we have ?"),
            new BotCommand(CustomBotCommand.HELP.value, "Help me")
    );

    public static final String HELP_TEXT = "This bot will connect you with ChatGpt latest api.\n" +
            "The following commands are available to you:\n\n" +
            CustomBotCommand.START.value + " - start the bot\n" +
            CustomBotCommand.MYKEY.value + " - use your own OpenAI API key\n" +
            CustomBotCommand.MODELIST.value + " - choose bot mode\n" +
            CustomBotCommand.HELP.value + " - help menu";
    public static final String MODE_LIST="List of available bot modes: \n/grammar \n/ai";

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData(CustomBotCommand.START.value);
        SET_KEY_BUTTON.setCallbackData(CustomBotCommand.MYKEY.value);
        MODE_BUTTON.setCallbackData(CustomBotCommand.MODELIST.value);
        HELP_BUTTON.setCallbackData(CustomBotCommand.HELP.value);


        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON,SET_KEY_BUTTON,MODE_BUTTON, HELP_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
