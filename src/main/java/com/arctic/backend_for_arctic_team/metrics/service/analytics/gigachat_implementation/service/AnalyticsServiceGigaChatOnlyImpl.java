package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;


import chat.giga.client.GigaChatClient;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.AnalyticsAdviceResponse;
import com.arctic.backend_for_arctic_team.metrics.service.analytics.AnalyticsService;
import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions.GigaChatClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "analytics.provider", havingValue = "gigachat", matchIfMissing = true)
public class AnalyticsServiceGigaChatOnlyImpl implements AnalyticsService {

    @Value("${gigachat_key}")
    private String gigaChatKey;

    private final Prompt prompt;
    private final GigaChatClient gigaChatClient;
    private final AnalyticsCache analyticsCache;

    @Override
    public AnalyticsAdviceResponse getAnalyticsAdvice(String indNum, Long expeditionId) {

        // проверка кеша
        Optional<String> cached = analyticsCache.get(indNum, expeditionId);

        if (cached.isPresent()) {
            log.info("Получен совет от нейросети для участника: {}, экспедиции: {} из кеша", indNum, expeditionId);
            return new AnalyticsAdviceResponse(cached.get());
        }

        String textForPrompt = prompt.getText(indNum, expeditionId);

        if (textForPrompt == null) return new AnalyticsAdviceResponse("Недостаточно данных для анализа.");

        try {
            log.debug("Начало запроса аналитики");
            String advice = getAdvice(textForPrompt);
            String formatAdvice = processAdviceFromGigaChat(advice);
            log.info("Получен совет от нейросети для участника: {}, экспедиции: {}", indNum, expeditionId);

            analyticsCache.put(indNum, expeditionId, formatAdvice);
            return new AnalyticsAdviceResponse(formatAdvice);
        } catch (HttpClientException ex) {
            log.error("GIGACHAT выбросил исключение, status: {}, message: {} for indNum: {}", ex.statusCode(), ex.getMessage(), indNum);
            throw new GigaChatClientException(ex.getMessage(), HttpStatus.resolve(ex.statusCode()));
        }
    }

    private String processAdviceFromGigaChat(String advice){
        if (advice == null) return "";

        return advice
                .replaceAll("(?m)^#{1,3}\\s*", "")
                .replaceAll("\\*\\*([^*]+)\\*\\*", "$1")
                .replaceAll("\\*([^*]+)\\*", "$1")
                .replaceAll("(?m)^\\s*[-*]\\s+", "  • ")
                .replaceAll("(?m)^\\s*(\\d+)\\.\\s+", "  $1. ")
                .replaceAll("\\n{3,}", "\n\n")
                .trim();
    }

    private String getAdvice(String textForPrompt){
        CompletionResponse response = gigaChatClient.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(textForPrompt)
                        .role(ChatMessageRole.USER)
                        .build())
                .build());
        log.debug("Получен ответ от Гигачата");
        return response.choices().getFirst().message().content();
    }
}
