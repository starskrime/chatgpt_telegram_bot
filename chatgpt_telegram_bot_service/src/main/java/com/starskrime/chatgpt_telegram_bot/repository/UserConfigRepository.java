package com.starskrime.chatgpt_telegram_bot.repository;

import com.starskrime.chatgpt_telegram_bot.entity.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig, Long> {
    Optional<UserConfig> findAllByTelegramUserId(String userId);

}
