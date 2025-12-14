package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "physiological_metrics")
@Builder
public class PhysiologicalMetrics {
    @Id
    @Column(name = "id")
    private String id;

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