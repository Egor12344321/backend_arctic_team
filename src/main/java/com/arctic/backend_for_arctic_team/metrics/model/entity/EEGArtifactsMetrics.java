package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eeg_artifacts_metrics")
@Builder
public class EEGArtifactsMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artifacts_channel_1")
    private Integer artifactsChannel1;

    @Column(name = "artifacts_channel_2")
    private Integer artifactsChannel2;

    @Column(name = "quality_channel_1")
    private Integer qualityChannel1;

    @Column(name = "quality_channel_2")
    private Integer qualityChannel2;
}
