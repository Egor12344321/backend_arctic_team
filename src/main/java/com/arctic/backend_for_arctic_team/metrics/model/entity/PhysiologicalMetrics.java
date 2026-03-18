package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "physiological_metrics")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysiologicalMetrics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expedition_id")
    private Long expeditionId;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "relax")
    private Double relax;

    @Column(name = "fatigue")
    private Double fatigue;

    @Column(name = "none")
    private Double none;

    @Column(name = "concentration")
    private Double concentration;

    @Column(name = "involvement")
    private Double involvement;

    @Column(name = "stress")
    private Double stress;

    @Column(name = "nfb_artifacts")
    private Integer nfbArtifacts;

    @Column(name = "cardio_artifacts")
    private Integer cardioArtifacts;
}