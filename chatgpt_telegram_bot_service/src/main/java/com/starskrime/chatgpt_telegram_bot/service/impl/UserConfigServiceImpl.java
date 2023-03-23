package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;
import com.starskrime.chatgpt_telegram_bot.enumeration.BotMod;
import com.starskrime.chatgpt_telegram_bot.repository.UserConfigRepository;
import com.starskrime.chatgpt_telegram_bot.service.UserConfigService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {
    private final UserConfigRepository userConfigRepository;



    @Override
    public Optional<UserConfig> getUserConfig(String userId) {
        return userConfigRepository.findAllByTelegramUserId(userId);
    }

    @Override
    public void saveUserConfig(UserConfig userConfig) {
        userConfigRepository.saveAndFlush(userConfig);
    }
}
