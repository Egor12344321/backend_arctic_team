package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.repository;

import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.dto.MetricsForAnalyticsDtoV2;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Deprecated
public class FetchingMetricsForAnalyticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<MetricsForAnalyticsDtoV2> getLastNSessions(String indNum, Long expeditionId, int limit) {
        String sql = """
        SELECT * FROM (
            SELECT
                n.session,
                AVG(n.alpha) as alpha,
                AVG(n.beta) as beta,
                AVG(n.theta) as theta,
                AVG(n.smr) as smr,
                AVG(p.concentration) as concentration,
                AVG(p.fatigue) as fatigue,
                AVG(p.relax) as relax,
                AVG(p.stress) as stress,
                AVG(e.attention) as attention,
                AVG(e.cognitive_load) as cognitiveLoad,
                AVG(pr.productivity) as productivity
            FROM nfb_metrics n
            LEFT JOIN physiological_metrics p
                ON n.session = p.session AND n.individual_number = p.individual_number
            LEFT JOIN emotional_metrics e
                ON n.session = e.session AND n.individual_number = e.individual_number
            LEFT JOIN productivity_metrics pr
                ON n.session = pr.session AND n.individual_number = pr.individual_number
            WHERE n.individual_number = ?
                AND n.expedition_id = ?
            GROUP BY n.session
            ORDER BY n.session DESC
            LIMIT ?
        ) AS last_sessions
        ORDER BY session ASC
    """;

        return jdbcTemplate.query(sql, (rs, i) ->
                        new MetricsForAnalyticsDtoV2(
                                (Double) rs.getObject("alpha"),
                                (Double) rs.getObject("beta"),
                                (Double) rs.getObject("theta"),
                                (Double) rs.getObject("smr"),
                                (Double) rs.getObject("concentration"),
                                (Double) rs.getObject("fatigue"),
                                (Double) rs.getObject("relax"),
                                (Double) rs.getObject("stress"),
                                (Double) rs.getObject("attention"),
                                (Double) rs.getObject("cognitiveLoad"),
                                (Double) rs.getObject("productivity")
                        ),
                indNum, expeditionId, limit
        );
    }
}
