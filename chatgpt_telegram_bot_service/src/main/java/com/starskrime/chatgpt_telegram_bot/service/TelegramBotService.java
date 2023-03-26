package com.starskrime.chatgpt_telegram_bot.service;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface TelegramBotService {

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Lets go"),
            new BotCommand("/mykey", "I want to use my own relation with ChatGPT"),
            new BotCommand("/modelist", "Lets see what we have ?"),
            new BotCommand("/help", "Help me")
    );

    String HELP_TEXT = "This bot will connect you with ChatGpt latest api.\n" +
            "The following commands are available to you:\n\n" +
            "/start - start the bot\n" +
            "/help - help menu";
    String MODE_LIST="List of available modes: \n/grammar \n/ai";

}
