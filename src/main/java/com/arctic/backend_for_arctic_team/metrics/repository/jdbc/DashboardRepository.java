package com.arctic.backend_for_arctic_team.metrics.repository.jdbc;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final JdbcTemplate jdbc;

    public List<SessionMetricsDto> getDashboardData(String indNum, Long expId) {
        String sql = """
            SELECT
                MIN(n.timestamp) as ts,
                AVG(n.alpha) as alpha,
                AVG(n.beta) as beta,
                AVG(n.theta) as theta,
                AVG(c.heart_rate) as heart_rate,
                AVG(c.stress_index) as stress_index,
                AVG(p.concentration) as concentration,
                AVG(p.fatigue) as fatigue,
                AVG(p.relax) as relax
            FROM nfb_metrics n
            LEFT JOIN cardio_metrics c
                ON n.session = c.session AND n.individual_number = c.individual_number
            LEFT JOIN physiological_metrics p
                ON n.session = p.session AND n.individual_number = p.individual_number
            WHERE n.individual_number = ? AND n.expedition_id = ?
            GROUP BY n.session
            ORDER BY n.session
            """;

        return jdbc.query(sql, (rs, i) -> {
            long ts = rs.getLong("ts");
            return new SessionMetricsDto(
                    formatLabel(ts),
                    rs.getDouble("alpha"),
                    rs.getDouble("beta"),
                    rs.getDouble("theta"),
                    rs.getDouble("heart_rate"),
                    rs.getDouble("stress_index"),
                    rs.getDouble("concentration"),
                    rs.getDouble("fatigue"),
                    rs.getDouble("relax")
            );
        }, indNum, expId);
    }

    private String formatLabel(long ts) {
        LocalDateTime dt = Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String date = dt.format(DateTimeFormatter.ofPattern("dd.MM"));
        int hour = dt.getHour();
        String timeOfDay = hour < 12 ? "Утро" : hour < 18 ? "День" : "Вечер";
        return date + " " + timeOfDay;
    }
}