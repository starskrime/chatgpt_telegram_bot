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



@Component
@Slf4j
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    private final TelegramBotConfiguration telegramBotConfiguration;

    public TelegramBotServiceImpl(TelegramBotConfiguration telegramBotConfiguration) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        sendMessage(telegramBotConfiguration.getAdminChatId(),"bakirtalibov","Hi. I just started");
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
        long userId = 0;
        String userName;
        String receivedMessage;

        System.out.println("Received: " + update.getMessage().toString());
        System.out.println("Text: " + update.getMessage().getText());

        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();




            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }

        //Temp code
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Received: " + update.getMessage().getText());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        //Temp code /
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                sendMessage(chatId, userName,"");
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            case "/mode-list":
                sendHelpText(chatId, "List of available messages: \n/GRAMMAR \n/AI");
                break;
            default: break;
        }
    }


    private void sendMessage(long chatId, String userName, String customMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (customMessage.isEmpty()){
            message.setText("Hi, " + userName + "! I'm a Telegram bot. Chat ID: "+ chatId);
            message.setReplyMarkup(TelegramButtonConfiguration.inlineMarkup());
        }else {
            message.setText(customMessage);
        }



        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            sendMessage(chatId,userName,e.getMessage());
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
            sendHelpText(chatId,e.getMessage());
            log.error(e.getMessage());
        }
    }
}
