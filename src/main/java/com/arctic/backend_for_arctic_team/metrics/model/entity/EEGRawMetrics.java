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
    private Float channel1;

    @Column(name = "channel_2")
    private Float channel2;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;
}
