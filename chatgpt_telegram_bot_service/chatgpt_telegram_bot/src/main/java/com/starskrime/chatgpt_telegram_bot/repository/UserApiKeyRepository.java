package com.starskrime.chatgpt_telegram_bot.repository;

import com.starskrime.chatgpt_telegram_bot.entity.UserApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApiKeyRepository extends JpaRepository<UserApiKey, Long> {

}
