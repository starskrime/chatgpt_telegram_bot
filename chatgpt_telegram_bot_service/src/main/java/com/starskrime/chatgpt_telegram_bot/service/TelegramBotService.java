package com.starskrime.chatgpt_telegram_bot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotService {
    void hasMessage(Update update);
    void hasCallBack(Update update);
    void sendMessage(long chatId, String userName, String customMessage);
    boolean setBotMode(Long chatId, String mode);
}
