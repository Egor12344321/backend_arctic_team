package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;

import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.dto.MetricsForAnalyticsDtoV2;
import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.repository.FetchingMetricsForAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Prompt {

    private final FetchingMetricsForAnalyticsRepository repository;
    private static final int SESSIONS_LIMIT = 5;

    public String getText(String indNum, Long expeditionId) {

        List<MetricsForAnalyticsDtoV2> sessions = repository.getLastNSessions(indNum, expeditionId, SESSIONS_LIMIT);

        if (sessions.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Проанализируй состояние участника экспедиции по последним ")
                .append(sessions.size()).append(" сессиям:\n\n");

        for (int i = 0; i < sessions.size(); i++) {
            MetricsForAnalyticsDtoV2 s = sessions.get(i);
            int sessionNum = i + 1;
            sb.append(String.format("""
                Сессия %d:
                - Альфа: %.2f | Бета: %.2f | Тета: %.2f | SMR: %.2f
                - Стресс: %.1f | Усталость: %.1f | Концентрация: %.1f
                - Продуктивность: %.1f | Внимание: %.1f
                
                """,
                    sessionNum,
                    safeDouble(s.alpha()), safeDouble(s.beta()),
                    safeDouble(s.theta()), safeDouble(s.smr()),
                    safeDouble(s.stress()), safeDouble(s.fatigue()),
                    safeDouble(s.concentration()),
                    safeDouble(s.productivity()), safeDouble(s.attention())
            ));
        }

        MetricsForAnalyticsDtoV2 first = sessions.getFirst();
        MetricsForAnalyticsDtoV2 last = sessions.getLast();

        double stressDelta = safeDouble(last.stress()) - safeDouble(first.stress());
        double productivityDelta = safeDouble(last.productivity()) - safeDouble(first.productivity());
        double concentrationDelta = safeDouble(last.concentration()) - safeDouble(first.concentration());

        sb.append(String.format("""
            Динамика (сессия %d → %d):
            - Стресс: %s%.1f%%
            - Продуктивность: %s%.1f%%
            - Концентрация: %s%.1f%%
            
            """,
                1, sessions.size(),
                stressDelta > 0 ? "+" : "", stressDelta,
                productivityDelta > 0 ? "+" : "", productivityDelta,
                concentrationDelta > 0 ? "+" : "", concentrationDelta
        ));

        sb.append("Дай краткий анализ состояния (3-5 предложений) и 2-3 рекомендации. ");
        sb.append("Без таблиц, без приветствий, без маркированных списков в анализе.");

        String text = sb.toString();
        log.debug("Текст запроса к GigaChat: {}", text);
        return text;
    }

    private double safeDouble(Double value) {
        return value != null ? value : 0;
    }
}