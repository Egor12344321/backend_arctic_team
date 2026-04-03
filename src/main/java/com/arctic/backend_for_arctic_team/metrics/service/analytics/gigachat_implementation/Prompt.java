package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation;


import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions.MetricsForAnalyticsNotFountException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Prompt {

    private final FetchingMetricsService fetchingMetricsService;
    private final ObjectMapper objectMapper;

    public String getText(String indNum, Long expeditionId) throws JsonProcessingException {
        MetricsForAnalyticsDto metrics = fetchingMetricsService.getMetricsForAnalytics(indNum, expeditionId);
//        if (metrics.cardioMetrics().isEmpty() || metrics.physiologicalMetrics().isEmpty() || metrics.productivityMetrics().isEmpty() || metrics.nfbMetrics().isEmpty()){
//            throw new MetricsForAnalyticsNotFountException("Метрики для аналитики не найдены", HttpStatus.BAD_REQUEST);
//        }
        String text = String.format(
                "Вы - эксперт в области нейронауки и анализа физиологических данных, специализирующийся на мониторинге состояния членов экспедиций в экстремальных условиях. Вашей задачей является анализ предоставленных метрик мозга и тела для одного члена экспедиции. Метрики включают:\n" +
                        "\n" +
                        "NFB-метрики (волны мозга): alpha (альфа-ритмы, связанные с расслаблением), beta (бета-ритмы, связанные с активностью и вниманием), theta (тета-ритмы, связанные с дремотой или творчеством), delta (дельта-ритмы, связанные с глубоким сном), smr (сенсомоторный ритм, связанный с фокусом).\n" +
                        "Физиологические метрики: relax (уровень расслабления), fatigue (усталость), concentration (концентрация), stress (стресс), involvement (вовлеченность).\n" +
                        "Кардио-метрики: heart_rate (частота сердечных сокращений), stress_index (индекс стресса), kaplan_index (индекс Каплана, связанный с вегетативным балансом).\n" +
                        "Метрики продуктивности: gravity (возможно, гравитационный фактор или весомость задач?), productivity (продуктивность), fatigue (усталость), concentration (концентрация), relaxation (расслабление).\n" +
                        "\n" +
                        "Данные предоставлены в формате списков словарей, отсортированных по timestamp (времени). Каждые метрики связаны с сессиями (session) и экспедицией (expedition_id).\n" +
                        "Данные для анализа:\n" +
                        "\n" +
                        "NFB-метрики: %s\n" +
                        "Физиологические метрики: %s\n" +
                        "Кардио-метрики: %s\n" +
                        "Метрики продуктивности: %s\n" +
                        "\n" +
                        "Проанализируйте эти данные шаг за шагом:\n" +
                        "\n" +
                        "Выявите тенденции во времени (рост/падение показателей, пики/спады).\n" +
                        "Найдите корреляции между метриками (например, высокий stress с высоким heart_rate или низкой concentration).\n" +
                        "Оцените общее состояние: уровень стресса, усталости, продуктивности, риски (например, переутомление, снижение внимания).\n" +
                        "Дайте рекомендации: практические советы по улучшению (например, техники релаксации, перерывы, медицинские проверки), адаптированные к контексту экспедиции.\n" +
                        "\n" +
                        "Выведите ответ в строгом структурированном формате\n" +
                        "Убедитесь, что анализ объективен, основан на данных и полезен для поддержания здоровья и продуктивности в экспедиции. Не добавляйте вымышленные данные. Напиши просто краткий анализ и все, без приветствия, без соглашения и тому подобного, исключительно краткий анализ и все. Только напиши ответ без таблиц, напиши сплошным текстом с разделением на абзацы",
                objectMapper.writeValueAsString(metrics.nfbMetrics()),
                objectMapper.writeValueAsString(metrics.physiologicalMetrics()),
                objectMapper.writeValueAsString(metrics.cardioMetrics()),
                objectMapper.writeValueAsString(metrics.productivityMetrics())
        );
        log.info(text);
        return text;
    }


}
