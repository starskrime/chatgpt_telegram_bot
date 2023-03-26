package com.starskrime.chatgpt_telegram_bot.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.starskrime.chatgpt_telegram_bot.client.OpenAIClient;
import com.starskrime.chatgpt_telegram_bot.configuration.OpenAIClientConfiguration;
import com.starskrime.chatgpt_telegram_bot.dto.*;
import com.starskrime.chatgpt_telegram_bot.service.ChatGptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ChatGptServiceImpl implements ChatGptService {
    private final OpenAIClient openAIClient;
    private final OpenAIClientConfiguration openAIClientConfig;

    private static final String ROLE_USER = "user";

    @Override
    public ChatGPTResponse chat(ChatRequest request, String apiKey){
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(request.getQuestion())
                .build();
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(Collections.singletonList(message))
                .build();
        apiKey = "Bearer " + apiKey;

        System.out.println("Sending request. Message: " + request.getQuestion() + " apiKey: " + apiKey);
        return openAIClient.chat(apiKey,chatGPTRequest);
    }

    @Override
    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest,String apiKey){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        apiKey = "Bearer " + apiKey;
        return openAIClient.createTranscription(apiKey,whisperTranscriptionRequest);
    }
}
