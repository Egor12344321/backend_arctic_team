package com.arctic.backend_for_arctic_team.metrics.repository.jdbc;


import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.NfbChartDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Repository
@RequiredArgsConstructor
public class NfbChartRepository {

    private final JdbcTemplate jdbc;

    public NfbChartDataDto getAggregatedData(String individualNumber, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(alpha) as avg_alpha,
                AVG(beta) as avg_beta,
                AVG(theta) as avg_theta,
                AVG(delta) as avg_delta,
                AVG(smr) as avg_smr
            FROM nfb_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
            ORDER BY session
            """;

        NfbChartDataDto result = new NfbChartDataDto();
        jdbc.query(sql, rs -> {
            result.labels().add(formatSession(rs.getLong("session")));
            result.alpha().add(rs.getDouble("avg_alpha"));
            result.beta().add(rs.getDouble("avg_beta"));
            result.theta().add(rs.getDouble("avg_theta"));
            result.delta().add(rs.getDouble("avg_delta"));
            result.smr().add(rs.getDouble("avg_smr"));
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