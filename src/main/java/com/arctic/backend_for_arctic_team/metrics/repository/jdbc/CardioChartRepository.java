package com.arctic.backend_for_arctic_team.metrics.repository.jdbc;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.CardioChartDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Repository
@RequiredArgsConstructor
public class CardioChartRepository {

    private final JdbcTemplate jdbc;

    public CardioChartDataDto getAggregatedData(String individualNumber, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(heart_rate) as avg_heart_rate,
                AVG(stress_index) as avg_stress_index,
                AVG(kaplan_index) as avg_kaplan_index
            FROM cardio_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
            ORDER BY session
            """;

        CardioChartDataDto result = new CardioChartDataDto();
        jdbc.query(sql, rs -> {
            result.labels().add(formatSession(rs.getLong("session")));
            result.heartRate().add(rs.getDouble("avg_heart_rate"));
            result.stressIndex().add(rs.getDouble("avg_stress_index"));
            result.kaplanIndex().add(rs.getDouble("avg_kaplan_index"));
        }, individualNumber, expeditionId);

        return result;
    }

    private String formatSession(long sessionTimestamp) {
        LocalDateTime dateTime = Instant.ofEpochSecond(sessionTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String date = dateTime.format(DateTimeFormatter.ofPattern("dd.MM"));
        int hour = dateTime.getHour();
        String timeOfDay;

        if (hour < 12) timeOfDay = "утро";
        else if (hour < 18) timeOfDay = "день";
        else timeOfDay = "вечер";

        return date + " " + timeOfDay;
    }
}