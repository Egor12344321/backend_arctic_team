package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.config;


import chat.giga.client.GigaChatClient;
import chat.giga.client.GigaChatClientImpl;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "analytics.provider", havingValue = "gigachat", matchIfMissing = true)
public class GigaChatClientConfig {

    @Value("${gigachat_key}")
    private String gigaChatKey;

    @Bean
    public GigaChatClient gigaChatClient(){
        return GigaChatClientImpl.builder()
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
    }
}
