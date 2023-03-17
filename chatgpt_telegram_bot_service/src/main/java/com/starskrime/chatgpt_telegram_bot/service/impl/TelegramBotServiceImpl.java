package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.configuration.TelegramBotConfiguration;
import com.starskrime.chatgpt_telegram_bot.configuration.TelegramButtonConfiguration;
import com.starskrime.chatgpt_telegram_bot.service.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.List;


@Component
@Slf4j
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    private final TelegramBotConfiguration telegramBotConfiguration;

    public TelegramBotServiceImpl(TelegramBotConfiguration telegramBotConfiguration) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        startBot(72055773,"bakirtalibov","HI. I just started");
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
    public void onUpdateReceived(@NotNull Update update) {
        long chatId = 0;
        long userId = 0; //это нам понадобится позже
        String userName = null;
        String receivedMessage;

        System.out.println("Received: " + update.getMessage().toString());
        System.out.println("Text: " + update.getMessage().getText());

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();




            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                startBot(chatId, userName,"");
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            default: break;
        }
    }


    private void startBot(long chatId, String userName,String customMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (customMessage.isEmpty()){
            message.setText("Hi, " + userName + "! I'm a Telegram bot.'");
            message.setReplyMarkup(TelegramButtonConfiguration.inlineMarkup());
        }else {
            message.setText(customMessage);
        }



        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
}
