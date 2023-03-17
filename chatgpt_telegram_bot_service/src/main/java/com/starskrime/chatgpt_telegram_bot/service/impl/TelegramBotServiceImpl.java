package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.configuration.TelegramBotConfiguration;
import com.starskrime.chatgpt_telegram_bot.service.TelegramBotService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Component
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    private final TelegramBotConfiguration telegramBotConfiguration;

    public TelegramBotServiceImpl(TelegramBotConfiguration telegramBotConfiguration) {
        this.telegramBotConfiguration = telegramBotConfiguration;
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfiguration.getBotToken();
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUpdateReceived(Update update) {

        String command = update.getMessage().getText();
        System.out.println("received" + command);
        if(command.equals("/hello")){
            String message = "Hello, dear friend!";

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try {
                execute(response);
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
    }
}
