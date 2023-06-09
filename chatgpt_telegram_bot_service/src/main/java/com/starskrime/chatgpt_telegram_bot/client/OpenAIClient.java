package com.starskrime.chatgpt_telegram_bot.client;

import com.starskrime.chatgpt_telegram_bot.configuration.OpenAIClientConfiguration;
import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTRequest;
import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTResponse;
import com.starskrime.chatgpt_telegram_bot.dto.WhisperTranscriptionRequest;
import com.starskrime.chatgpt_telegram_bot.dto.WhisperTranscriptionResponse;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "openai-service",
        url = "${openai-service.urls.base-url}",
        configuration = OpenAIClientConfiguration.class
)

public interface OpenAIClient {

    @PostMapping(value = "${openai-service.urls.chat-url}", headers = {"Content-Type=application/json"})
    ChatGPTResponse chat(@RequestHeader("Authorization") String apiKey, @RequestBody ChatGPTRequest chatGPTRequest);

    @PostMapping(value = "${openai-service.urls.create-transcription-url}", headers = {"Content-Type=multipart/form-data"})
    WhisperTranscriptionResponse createTranscription(@RequestHeader("Authorization") String apiKey,@ModelAttribute WhisperTranscriptionRequest whisperTranscriptionRequest);

}