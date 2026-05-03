package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionMetricsDto {
    private Integer session;
    private String date;
    private String timeOfDay;

    private Integer totalCognitive;
    private Integer totalPhysiological;
    private Integer totalPsychological;
    private Integer totalIndex;
    private String objectiveFatigue;
    private String objectiveStress;

    private Integer objectiveCognitive;
    private Integer objectivePhysiological;
    private Integer objectivePsychological;
    private Integer subjectiveCognitive;
    private Integer subjectivePhysiological;
    private Integer subjectivePsychological;

    private Double alpha;
    private Double beta;
    private Double theta;
    private Double smr;

    private Double concentration;
    private Double fatigue;
    private Double relax;
    private Double stress;

    private Double attention;
    private Double cognitiveLoad;
    private Double relaxation;
    private Double selfControl;
    private Double cognitiveControl;

    private Double productivity;

    private Integer durationMinutes;
}