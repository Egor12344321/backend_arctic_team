package com.arctic.backend_for_arctic_team.metrics.repository.jdbc;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;



@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final JdbcTemplate jdbc;

    public List<SessionMetricsDto> getDashboardData(String indNum, Long expeditionId) {
        List<SessionMetricsDto> results = getSessionResults(indNum, expeditionId);
        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, NFBData> nfbMap = getNFBMetrics(indNum, expeditionId);

        Map<Long, PhysiologicalData> physioMap = getPhysiologicalMetrics(indNum, expeditionId);

        Map<Long, EmotionalData> emotionalMap = getEmotionalMetrics(indNum, expeditionId);

        Map<Long, ProductivityData> productivityMap = getProductivityMetrics(indNum, expeditionId);

        for (SessionMetricsDto dto : results) {
            NFBData nfb = nfbMap.get(dto.getSession());
            if (nfb != null) {
                dto.setAlpha(nfb.alpha);
                dto.setBeta(nfb.beta);
                dto.setTheta(nfb.theta);
                dto.setSmr(nfb.smr);
            }

            PhysiologicalData physio = physioMap.get(dto.getSession());
            if (physio != null) {
                dto.setConcentration(physio.concentration);
                dto.setFatigue(physio.fatigue);
                dto.setRelax(physio.relax);
                dto.setStress(physio.stress);
            }

            EmotionalData emotional = emotionalMap.get(dto.getSession());
            if (emotional != null) {
                dto.setAttention(emotional.attention);
                dto.setCognitiveLoad(emotional.cognitiveLoad);
                dto.setRelaxation(emotional.relaxation);
                dto.setSelfControl(emotional.selfControl);
                dto.setCognitiveControl(emotional.cognitiveControl);
            }

            ProductivityData prod = productivityMap.get(dto.getSession());
            if (prod != null) {
                dto.setProductivity(prod.productivity);
            }
        }

        return results;
    }

    private List<SessionMetricsDto> getSessionResults(String indNum, Long expeditionId) {
        String sql = """
            SELECT
                session,
                end_time,
                total_cognitive,
                total_physiological,
                total_psychological,
                total_index,
                objective_fatigue,
                objective_stress,
                objective_cognitive,
                objective_physiological,
                objective_psychological,
                subjective_cognitive,
                subjective_physiological,
                subjective_psychological,
                duration_minutes
            FROM session_results
            WHERE individual_number = ? AND expedition_id = ?
            ORDER BY session ASC
        """;

        return jdbc.query(sql, (rs, i) -> SessionMetricsDto.builder()
                .session((long) rs.getInt("session"))
                .date(formatDate(rs.getInt("end_time")))
                .timeOfDay(formatTimeOfDay(rs.getInt("end_time")))
                .totalCognitive(rs.getInt("total_cognitive"))
                .totalPhysiological(rs.getInt("total_physiological"))
                .totalPsychological(rs.getInt("total_psychological"))
                .totalIndex(rs.getInt("total_index"))
                .objectiveFatigue(rs.getString("objective_fatigue"))
                .objectiveStress(rs.getString("objective_stress"))
                .objectiveCognitive(rs.getInt("objective_cognitive"))
                .objectivePhysiological(rs.getInt("objective_physiological"))
                .objectivePsychological(rs.getInt("objective_psychological"))
                .subjectiveCognitive(rs.getInt("subjective_cognitive"))
                .subjectivePhysiological(rs.getInt("subjective_physiological"))
                .subjectivePsychological(rs.getInt("subjective_psychological"))
                .durationMinutes(rs.getInt("duration_minutes"))
                .build(), indNum, expeditionId);
    }


    private Map<Long, NFBData> getNFBMetrics(String indNum, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(alpha) as alpha,
                AVG(beta) as beta,
                AVG(theta) as theta,
                AVG(smr) as smr
            FROM nfb_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
        """;

        return jdbc.query(sql, rs -> {
            Map<Long, NFBData> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("session"), new NFBData(
                        rs.getDouble("alpha"),
                        rs.getDouble("beta"),
                        rs.getDouble("theta"),
                        rs.getDouble("smr")
                ));
            }
            return map;
        }, indNum, expeditionId);
    }

    private Map<Long, PhysiologicalData> getPhysiologicalMetrics(String indNum, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(concentration) as concentration,
                AVG(fatigue) as fatigue,
                AVG(relax) as relax,
                AVG(stress) as stress
            FROM physiological_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
        """;

        return jdbc.query(sql, rs -> {
            Map<Long, PhysiologicalData> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("session"), new PhysiologicalData(
                        rs.getDouble("concentration"),
                        rs.getDouble("fatigue"),
                        rs.getDouble("relax"),
                        rs.getDouble("stress")
                ));
            }
            return map;
        }, indNum, expeditionId);
    }

    private Map<Long, EmotionalData> getEmotionalMetrics(String indNum, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(attention) as attention,
                AVG(cognitive_load) as cognitive_load,
                AVG(relaxation) as relaxation,
                AVG(self_control) as self_control,
                AVG(cognitive_control) as cognitive_control
            FROM emotional_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
        """;

        return jdbc.query(sql, rs -> {
            Map<Long, EmotionalData> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("session"), new EmotionalData(
                        rs.getDouble("attention"),
                        rs.getDouble("cognitive_load"),
                        rs.getDouble("relaxation"),
                        rs.getDouble("self_control"),
                        rs.getDouble("cognitive_control")
                ));
            }
            return map;
        }, indNum, expeditionId);
    }

    private Map<Long, ProductivityData> getProductivityMetrics(String indNum, Long expeditionId) {
        String sql = """
            SELECT
                session,
                AVG(productivity) as productivity
            FROM productivity_metrics
            WHERE individual_number = ? AND expedition_id = ?
            GROUP BY session
        """;

        return jdbc.query(sql, rs -> {
            Map<Long, ProductivityData> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("session"), new ProductivityData(
                        rs.getDouble("productivity")
                ));
            }
            return map;
        }, indNum, expeditionId);
    }

    private String formatDate(Integer endTime) {
        if (endTime == null) return "??.??";
        long ts = endTime * 1000L;
        LocalDateTime dt = Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dt.format(DateTimeFormatter.ofPattern("dd.MM"));
    }

    private String formatTimeOfDay(Integer endTime) {
        if (endTime == null) return "День";
        long ts = endTime * 1000L;
        int hour = Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault()).toLocalDateTime().getHour();
        if (hour < 12) return "Утро";
        if (hour < 18) return "День";
        return "Вечер";
    }

    private record NFBData(double alpha, double beta, double theta, double smr) {}
    private record PhysiologicalData(double concentration, double fatigue, double relax, double stress) {}
    private record EmotionalData(double attention, double cognitiveLoad, double relaxation, double selfControl, double cognitiveControl) {}
    private record ProductivityData(double productivity) {}
}