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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "analytics.provider", havingValue = "gigachat", matchIfMissing = true)
public class AnalyticsServiceGigaChatOnlyImpl implements AnalyticsService {

    @Value("${gigachat_key}")
    private String gigaChatKey;

    private final Prompt prompt;

    @Override
    public AnalyticsAdviceResponse getAnalyticsAdvice(String indNum, Long expeditionId) {
        GigaChatClient client = GigaChatClientImpl.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(
                                AuthClientBuilder.OAuthBuilder.builder()
                                        .verifySslCerts(false) //изменить на true в проде
                                        .scope(Scope.GIGACHAT_API_PERS)
                                        .authKey(gigaChatKey)
                                        .build())
                        .build())
                .build();

        try {
            CompletionResponse response = client.completions(CompletionRequest.builder()
                    .model(ModelName.GIGA_CHAT)
                    .message(ChatMessage.builder()
                            .content(prompt.getText(indNum, expeditionId))
                            .role(ChatMessageRole.USER)
                            .build())
                    .build());
            String advice = response.choices().getFirst().message().content();
            return new AnalyticsAdviceResponse(advice);
        } catch (HttpClientException ex) {
            log.error("GIGACHAT выбросил исключение, status: {}, message: {}", ex.statusCode(), ex.getMessage());
            throw new GigaChatClientException(ex.getMessage(), HttpStatus.resolve(ex.statusCode()));
        }
    }
}
