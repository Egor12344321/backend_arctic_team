package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;

import com.arctic.backend_for_arctic_team.metrics.model.entity.SessionResults;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.SessionResultsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Prompt {

    private final SessionResultsRepository repository;
    private static final int SESSIONS_LIMIT = 5;

    public String getText(String indNum, Long expeditionId) {
        List<SessionResults> sessions = repository.findTop5ByIndividualNumberAndExpeditionIdOrderBySessionDesc(indNum, expeditionId);

        if (sessions.isEmpty()) {
            return null;
        }

        sessions = sessions.reversed();

        StringBuilder sb = new StringBuilder();
        sb.append("Проанализируй состояние участника экспедиции по последним ").append(sessions.size()).append(" сессиям.\n\n");

        sb.append("| Сессия | Когнитивная | Физиологическая | Психологическая | Общий индекс | Усталость | Стресс |\n");

        for (int i = 0; i < sessions.size(); i++) {
            SessionResults s = sessions.get(i);
            sb.append(String.format("| %d | %d | %d | %d | %d | %s | %s |\n",
                    i + 1,
                    s.getTotalCognitive(),
                    s.getTotalPhysiological(),
                    s.getTotalPsychological(),
                    s.getTotalIndex(),
                    s.getObjectiveFatigue(),
                    s.getObjectiveStress()
            ));
        }

        SessionResults first = sessions.getFirst();
        SessionResults last = sessions.getLast();

        sb.append("\n## Динамика\n");
        sb.append(String.format("- Когнитивная нагрузка: %d → %d (%+d)\n",
                first.getTotalCognitive(), last.getTotalCognitive(),
                last.getTotalCognitive() - first.getTotalCognitive()));
        sb.append(String.format("- Физиологическая нагрузка: %d → %d (%+d)\n",
                first.getTotalPhysiological(), last.getTotalPhysiological(),
                last.getTotalPhysiological() - first.getTotalPhysiological()));
        sb.append(String.format("- Психологическая нагрузка: %d → %d (%+d)\n",
                first.getTotalPsychological(), last.getTotalPsychological(),
                last.getTotalPsychological() - first.getTotalPsychological()));
        sb.append(String.format("- Общий индекс: %d → %d (%+d)\n",
                first.getTotalIndex(), last.getTotalIndex(),
                last.getTotalIndex() - first.getTotalIndex()));

        sb.append("\n## Объективная vs Субъективная оценка\n");
        sb.append(String.format("- Когнитивная: объективная %d vs субъективная %d (разница %+d)\n",
                last.getObjectiveCognitive(), last.getSubjectiveCognitive(),
                last.getObjectiveCognitive() - last.getSubjectiveCognitive()));
        sb.append(String.format("- Физиологическая: объективная %d vs субъективная %d (разница %+d)\n",
                last.getObjectivePhysiological(), last.getSubjectivePhysiological(),
                last.getObjectivePhysiological() - last.getSubjectivePhysiological()));
        sb.append(String.format("- Психологическая: объективная %d vs субъективная %d (разница %+d)\n",
                last.getObjectivePsychological(), last.getSubjectivePsychological(),
                last.getObjectivePsychological() - last.getSubjectivePsychological()));

        sb.append("\nДай краткий анализ состояния (3-5 предложений) и 2-3 рекомендации. Обязательно обрати внимание на последнюю сессию и скажи касательно нее.");
        sb.append("Если объективные оценки выше субъективных — человек не замечает перегрузки. ");
        sb.append("Ответь без приветствий. Без таблиц! Только текст. Только анализ");

        String text = sb.toString();
        log.debug("Текст запроса к GigaChat: {}", text);
        return text;
    }
}