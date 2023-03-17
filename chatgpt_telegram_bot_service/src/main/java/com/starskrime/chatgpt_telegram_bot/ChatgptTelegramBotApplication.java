package com.starskrime.chatgpt_telegram_bot;

import com.starskrime.chatgpt_telegram_bot.service.impl.TelegramBotServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@Slf4j
public class ChatgptTelegramBotApplication {

    @Autowired
    TelegramBotServiceImpl bot;

    public static void main(String[] args) {
        SpringApplication.run(ChatgptTelegramBotApplication.class, args);
    }

}
