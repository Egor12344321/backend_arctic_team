package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "session_results")
public class SessionResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_number", nullable = false)
    private String individualNumber;

    @Column(name = "expedition_id", nullable = false)
    private Long expeditionId;

    @Column(name = "session", nullable = false)
    private Long session;

    @Column(name = "objective_cognitive")
    private Integer objectiveCognitive;

    @Column(name = "objective_psychological")
    private Integer objectivePsychological;

    @Column(name = "objective_physiological")
    private Integer objectivePhysiological;

    @Column(name = "subjective_cognitive")
    private Integer subjectiveCognitive;

    @Column(name = "subjective_psychological")
    private Integer subjectivePsychological;

    @Column(name = "subjective_physiological")
    private Integer subjectivePhysiological;

    @Column(name = "total_index")
    private Integer totalIndex;

    @Column(name = "average_objective")
    private Integer averageObjective;

    @Column(name = "average_subjective")
    private Integer averageSubjective;

    @Column(name = "total_cognitive")
    private Integer totalCognitive;

    @Column(name = "total_physiological")
    private Integer totalPhysiological;

    @Column(name = "total_psychological")
    private Integer totalPsychological;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "session_category")
    private String sessionCategory;

    @Column(name = "comment")
    private String comment;

    @Column(name = "objective_fatigue")
    private String objectiveFatigue;

    @Column(name = "objective_stress")
    private String objectiveStress;

    @Column(name = "passing_prematurely")
    private Boolean passingPrematurely;
}