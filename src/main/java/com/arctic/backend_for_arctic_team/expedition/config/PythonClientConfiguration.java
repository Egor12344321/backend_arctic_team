package com.arctic.backend_for_arctic_team.expedition.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PythonClientConfiguration {

    private final ChartsServiceProperties prop;


    @Bean
    public RestClient pythonRestClient(){
        return RestClient.builder()
                .baseUrl(prop.getUrl())
                .requestFactory(factory())
                .build();
    }

    private ClientHttpRequestFactory factory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectionRequestTimeout(5000);
        factory.setReadTimeout(5000);

        return factory;
    }

    static class LoggingInterceptor implements ClientHttpRequestInterceptor{
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            log.info("Запрос в Python-service: {} {}", request.getMethod(), request.getURI());

            long startTime = System.currentTimeMillis();

            ClientHttpResponse response = execution.execute(request, body);

            long duration = System.currentTimeMillis() - startTime;
            log.info("Response от Python за {} ms, статус: {}", duration, response.getStatusCode());
            return response;
        }
    }
}
