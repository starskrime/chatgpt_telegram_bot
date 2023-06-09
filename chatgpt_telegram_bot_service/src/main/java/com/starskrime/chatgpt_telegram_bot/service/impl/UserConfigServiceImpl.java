package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;
import com.starskrime.chatgpt_telegram_bot.repository.UserConfigRepository;
import com.starskrime.chatgpt_telegram_bot.service.UserConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserConfigServiceImpl implements UserConfigService {
    private final UserConfigRepository userConfigRepository;



    @Override
    public Optional<UserConfig> getUserConfig(Long userId) {
        return userConfigRepository.findAllByTelegramUserId(String.valueOf(userId));
    }

    @Override
    public void saveUserConfig(UserConfig userConfig) {
        userConfigRepository.saveAndFlush(userConfig);
    }
}
