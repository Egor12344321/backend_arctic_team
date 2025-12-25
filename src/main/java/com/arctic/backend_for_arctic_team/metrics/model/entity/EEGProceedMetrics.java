package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eeg_proceed_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EEGProceedMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_1")
    private Double channel1;

    @Column(name = "channel_2")
    private Double channel2;

}
