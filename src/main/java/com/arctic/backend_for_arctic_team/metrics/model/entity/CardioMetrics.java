package com.arctic.backend_for_arctic_team.metrics.model.entity;

import com.arctic.backend_for_arctic_team.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cardio_metrics")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardioMetrics {
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