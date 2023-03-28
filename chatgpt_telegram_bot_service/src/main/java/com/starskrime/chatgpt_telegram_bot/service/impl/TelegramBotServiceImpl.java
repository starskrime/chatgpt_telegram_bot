package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.configuration.TelegramBotConfiguration;
import com.starskrime.chatgpt_telegram_bot.configuration.TelegramButtonConfiguration;
import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTResponse;
import com.starskrime.chatgpt_telegram_bot.dto.ChatRequest;
import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;
import com.starskrime.chatgpt_telegram_bot.enumeration.CustomBotCommand;
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

import static com.starskrime.chatgpt_telegram_bot.configuration.TelegramButtonConfiguration.*;


@Component
@Slf4j
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    Map<Long,String> lastMessage = new HashMap<>();

    Long chatId;
    Long userId;
    String userName;
    String receivedMessage;
    Optional<UserConfig> userConfig;
    private final TelegramBotConfiguration telegramBotConfiguration;
    private final UserConfigService userConfigService;
    private final ChatGptService chatGptService;

    public TelegramBotServiceImpl(TelegramBotConfiguration telegramBotConfiguration, UserConfigService userConfigService, ChatGptService chatGptService) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.userConfigService = userConfigService;
        this.chatGptService = chatGptService;
        sendMessage(telegramBotConfiguration.getAdminChatId(),null,"Hi. I just restarted");
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
        log.info("UPDATE: " + update.toString());

        if (update.getMessage()==null){
            hasCallBack(update);
        }else{
            hasMessage(update);
        }
    }

    private void availableFeatures(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                sendMessage(chatId, userName,"");
                break;
            case "/mykey":
                sendHelpText(chatId, "Please specify your own ChatGPT API key.");
                break;
            case "/modelist":
                sendHelpText(chatId, MODE_LIST);
                break;
            case "/grammar":
                setBotMode(chatId,"grammar");
                break;
            case "/ai":
                setBotMode(chatId,"ai");
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            default:
                break;
        }
    }

    @Override
    public void sendMessage(long chatId, String userName, String customMessage) {
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

    @Override
    public boolean setBotMode(Long chatId, String mode) {
        if(lastMessage.get(chatId).equals(CustomBotCommand.MODELIST.value)) {
            userConfig.get().setBotMode(BotMode.valueOf(receivedMessage));
            userConfigService.saveUserConfig(userConfig.get());
            sendMessage(chatId,userName,"Mode changed to: " + receivedMessage);
            return true;
        }
        return false;
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

    @Override
    public void hasMessage(@NotNull Update update) {

        chatId = update.getMessage().getChatId();
        userId = update.getMessage().getFrom().getId();
        userName=update.getMessage().getFrom().getFirstName();
        receivedMessage  = update.getMessage().getText();
        userConfig = userConfigService.getUserConfig(userId);


        if (receivedMessage.startsWith("/")) {
            availableFeatures(receivedMessage, chatId, userName);

        }else if(receivedMessage.startsWith("sk-") && lastMessage.get(chatId).equals(CustomBotCommand.MYKEY.value)) {
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
            sendMessage(chatId,userName,"Api key is successfully saved.");

        }else if (userConfig.isEmpty()){
            sendMessage(chatId,userName,"ChatGPT api key is not specified. Please use the link to get api key : https://help.openai.com/en/articles/4936850-where-do-i-find-my-secret-api-key and send /mykey command to specify api key next.");

        }else {
            ChatRequest request = new ChatRequest();

            if (userConfig.get().getBotMode().value.equals("grammar")){
                receivedMessage = "You are teacher of English language.Check grammar of my sentence: " + receivedMessage;
            }

            request.setQuestion(receivedMessage);
            ChatGPTResponse response = chatGptService.chat(request,userConfig.get().getChatGptApiKey());
            sendMessage(chatId,userName,response.getChoices().get(0).getMessage().getContent());
        }

        lastMessage.put(chatId,receivedMessage);
    }

    @Override
    public void hasCallBack(@NotNull Update update) {
        chatId = update.getCallbackQuery().getFrom().getId();
        userId = update.getCallbackQuery().getFrom().getId();
        userName = String.valueOf(update.getCallbackQuery().getFrom().getUserName());
        receivedMessage=update.getCallbackQuery().getData();
        userConfig = userConfigService.getUserConfig(userId);

        availableFeatures(receivedMessage, chatId, userName);
        lastMessage.put(chatId,receivedMessage);
    }
}
