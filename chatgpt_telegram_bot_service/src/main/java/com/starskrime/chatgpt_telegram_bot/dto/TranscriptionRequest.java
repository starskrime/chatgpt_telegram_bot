package com.starskrime.chatgpt_telegram_bot.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TranscriptionRequest {
    private MultipartFile file;
}
