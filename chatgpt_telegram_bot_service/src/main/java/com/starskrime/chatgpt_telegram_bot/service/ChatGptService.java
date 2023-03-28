package com.starskrime.chatgpt_telegram_bot.service;

import com.starskrime.chatgpt_telegram_bot.dto.ChatGPTResponse;
import com.starskrime.chatgpt_telegram_bot.dto.ChatRequest;
import com.starskrime.chatgpt_telegram_bot.dto.TranscriptionRequest;
import com.starskrime.chatgpt_telegram_bot.dto.WhisperTranscriptionResponse;

public interface ChatGptService {
    ChatGPTResponse chat(ChatRequest request, String apiKey);
    WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest, String apiKey);
}
