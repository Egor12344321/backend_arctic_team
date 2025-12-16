package com.arctic.backend_for_arctic_team.metrics.model.entity;

import com.arctic.backend_for_arctic_team.auth.entity.User;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "cardio_metrics")
@Builder
public class CardioMetrics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "heartRate")
    private Double heartRate;

    @Column(name = "hasArtifacts")
    private Integer hasArtifacts;

    @Column(name = "kaplanIndex")
    private Double kaplanIndex;

    @Column(name = "metricsAvailable")
    private Integer metricsAvailable;

    @Column(name = "motionArtifacts")
    private Integer motionArtifacts;

    @Column(name = "skinContact")
    private Integer skinContact;

    @Column(name = "stressIndex")
    private Double stressIndex;

}