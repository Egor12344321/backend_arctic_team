package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "eeg_raw_metrics")
@Builder
public class EEGRawMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_1")
    private Double channel1;

    @Column(name = "channel_2")
    private Double channel2;
}
