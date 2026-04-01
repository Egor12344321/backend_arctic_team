package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cardio_metrics_compressed")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardioMetricsCompressed {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "expedition_id")
    private Long expeditionId;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "heart_rate")
    private Double heartRate;

    @Column(name = "has_artifacts")
    private Integer hasArtifacts;

    @Column(name = "kaplan_index")
    private Double kaplanIndex;

    @Column(name = "metrics_available")
    private Integer metricsAvailable;

    @Column(name = "motion_artifacts")
    private Integer motionArtifacts;

    @Column(name = "skin_contact")
    private Integer skinContact;

    @Column(name = "stress_index")
    private Double stressIndex;

}