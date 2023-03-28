package com.starskrime.chatgpt_telegram_bot.service;

import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;

import java.util.Optional;

public interface UserConfigService {
    Optional<UserConfig> getUserConfig(Long userId);
    void saveUserConfig(UserConfig userConfig);
}
