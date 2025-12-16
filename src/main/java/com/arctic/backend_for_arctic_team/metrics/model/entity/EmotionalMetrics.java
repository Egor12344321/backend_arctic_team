package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "emotional_metrics")
@Builder
public class EmotionalMetrics {
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

    @Column(name = "attention")
    private Double attention;

    @Column(name = "relaxation")
    private Double relaxation;

    @Column(name = "cognitiveLoad")
    private Double cognitiveLoad;

    @Column(name = "cognitiveControl")
    private Double cognitiveControl;

    @Column(name = "selfControl")
    private Double selfControl;

}