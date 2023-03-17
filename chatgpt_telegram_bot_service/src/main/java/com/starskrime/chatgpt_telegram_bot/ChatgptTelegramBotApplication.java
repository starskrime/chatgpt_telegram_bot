package com.starskrime.chatgpt_telegram_bot;

import com.starskrime.chatgpt_telegram_bot.service.impl.TelegramBotServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatgptTelegramBotApplication {

    private final TelegramBotServiceImpl telegramBotService;

    public ChatgptTelegramBotApplication(TelegramBotServiceImpl telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatgptTelegramBotApplication.class, args);
    }

}
