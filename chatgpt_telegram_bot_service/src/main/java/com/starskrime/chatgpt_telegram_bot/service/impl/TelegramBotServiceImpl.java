package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.configuration.TelegramBotConfiguration;
import com.starskrime.chatgpt_telegram_bot.configuration.TelegramButtonConfiguration;
import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTRequest;
import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTResponse;
import com.starskrime.chatgpt_telegram_bot.dto.ChatRequest;
import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;
import com.starskrime.chatgpt_telegram_bot.enumeration.BotMode;
import com.starskrime.chatgpt_telegram_bot.service.ChatGptService;
import com.starskrime.chatgpt_telegram_bot.service.TelegramBotService;
import com.starskrime.chatgpt_telegram_bot.service.UserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@Slf4j
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    Map<String,String> lastMessage = new HashMap<>();

    private final TelegramBotConfiguration telegramBotConfiguration;
    private final UserConfigService userConfigService;
    private final ChatGptService chatGptService;

    public TelegramBotServiceImpl(TelegramBotConfiguration telegramBotConfiguration, UserConfigService userConfigService, ChatGptService chatGptService) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.userConfigService = userConfigService;
        this.chatGptService = chatGptService;
        sendMessage(telegramBotConfiguration.getAdminChatId(),"bakirtalibov","Hi. I just started");
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
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
        String chatId = update.getMessage().getChatId().toString();
        String userId = update.getMessage().getFrom().getId().toString();
        String userName=update.getMessage().getFrom().getFirstName();
        String receivedMessage  = update.getMessage().getText();
        Optional<UserConfig> userConfig = userConfigService.getUserConfig(userId);

        if (update.getMessage().getText().startsWith("/")) {
            availableFeatures(receivedMessage, Long.parseLong(chatId), userName);
        }else  if (receivedMessage.equals(BotMode.AI) && lastMessage.get(chatId).contains("/setmode") ) {
            userConfig.get().setBotMode(BotMode.valueOf(receivedMessage));
            userConfigService.saveUserConfig(userConfig.get());
            sendMessage(Long.parseLong(chatId),"","Mode changed to: " + receivedMessage);

        } else  if (receivedMessage.startsWith("sk-") && lastMessage.get(chatId).contains("/setkey")) {
            UserConfig currentUser;
            if (userConfig.isPresent()){
                currentUser = userConfig.get();
                currentUser.setChatGptApiKey(receivedMessage);
            }else {
                currentUser = new UserConfig();
                currentUser.setTelegramUserId(String.valueOf(userId));
                currentUser.setChatGptApiKey(receivedMessage);
                currentUser.setBotMode(BotMode.AI);
            }
            userConfigService.saveUserConfig(currentUser);
            sendMessage(Long.parseLong(chatId),"","Api Key is successfully configured.");

        }else if (userConfig.isEmpty()){
            sendMessage(Long.parseLong(chatId),"","Api Key is not specified.Please use /setkey command to specify api key.");
        }else if(update.hasMessage()) {
            ChatRequest request = new ChatRequest();
            request.setQuestion(update.getMessage().getText());
            ChatGPTResponse response = chatGptService.chat(request,userConfig.get().getChatGptApiKey());
            System.out.println("RESPONSE: "+ response.toString());
            sendMessage(Long.parseLong(chatId),userId,response.getObject());
        }




        else if (update.hasCallbackQuery()) {
            receivedMessage = update.getCallbackQuery().getData();
            availableFeatures(receivedMessage, Long.parseLong(chatId), userName);
        }

        //Temp code
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Received: " + update.getMessage().getText());
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
        //Temp code /
        lastMessage.put(chatId,receivedMessage);
    }

    private void availableFeatures(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                sendMessage(chatId, userName,"");
                break;
            case "/setKey":
                sendHelpText(chatId, "Please specify you ChatGPT API key");
                break;
            case "/modeList":
                sendHelpText(chatId, MODE_LIST);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            default:
                break;
        }
    }


    private void sendMessage(long chatId, String userName, String customMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (customMessage.isEmpty()){
            message.setText("Hi, " + userName + "! I'm a ChatGPT bot in Telegram. Chat ID: "+ chatId);
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
