package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation;


import chat.giga.client.GigaChatClient;
import chat.giga.client.GigaChatClientImpl;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "analytics.provider", havingValue = "gigachat", matchIfMissing = true)
public class AnalyticsServiceGigaChatOnlyImpl implements AnalyticsService {

    @Value("${gigachat_key}")
    private String gigaChatKey;

    private final Prompt prompt;
    private final GigaChatClient gigaChatClient;

    @Override
    public AnalyticsAdviceResponse getAnalyticsAdvice(String indNum, Long expeditionId) {
        try {
            CompletionResponse response = gigaChatClient.completions(CompletionRequest.builder()
                    .model(ModelName.GIGA_CHAT)
                    .message(ChatMessage.builder()
                            .content(prompt.getText(indNum, expeditionId))
                            .role(ChatMessageRole.USER)
                            .build())
                    .build());
            String advice = response.choices().getFirst().message().content();
            String formatAdvice = processAdviceFromGigaChat(advice);
            log.info("Получен совет от нейросети для участника: {}, экспедиции: {}", indNum, expeditionId);
            return new AnalyticsAdviceResponse(formatAdvice);
        } catch (HttpClientException ex) {
            log.error("GIGACHAT выбросил исключение, status: {}, message: {} for indNum: {}", ex.statusCode(), ex.getMessage(), indNum);
            throw new GigaChatClientException(ex.getMessage(), HttpStatus.resolve(ex.statusCode()));
        } catch (JsonProcessingException ex){
            log.error("JsonProcessingException for indNum: {}", indNum);
            throw new GigaChatClientException("Ошибка преобразования метрик в JSON", HttpStatus.INTERNAL_SERVER_ERROR);
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
}
