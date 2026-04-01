package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotional_metrics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionalMetrics {
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

    @Column(name = "attention")
    private Double attention;

    @Column(name = "relaxation")
    private Double relaxation;

    @Column(name = "cognitive_load")
    private Double cognitiveLoad;

    @Column(name = "cognitive_control")
    private Double cognitiveControl;

    @Column(name = "self_control")
    private Double selfControl;

}