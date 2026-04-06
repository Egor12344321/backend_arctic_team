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

    @Column(name = "expedition_id")
    private Long expeditionId;

    @Column(name = "artifacts_channel_1")
    private Boolean artifactsChannel1;

    @Column(name = "artifacts_channel_2")
    private Boolean artifactsChannel2;

    @Column(name = "quality_channel_1")
    private Float qualityChannel1;

    @Column(name = "quality_channel_2")
    private Float qualityChannel2;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;
}
