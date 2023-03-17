package com.starskrime.chatgpt_telegram_bot.service;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface TelegramBotService {

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );

    String HELP_TEXT = "This bot will connect you with ChatGpt latest api" +
            "The following commands are available to you:\n\n" +
            "/start - start the bot\n" +
            "/help - help menu";

}
