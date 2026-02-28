package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mems_metrics")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemsMetrics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expedition_id")
    private String expeditionId;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "accelerometerX")
    private Double accelerometerX;

    @Column(name = "accelerometerY")
    private Double accelerometerY;

    @Column(name = "accelerometerZ")
    private Double accelerometerZ;

    @Column(name = "gyroscopeX")
    private Double gyroscopeX;

    @Column(name = "gyroscopeY")
    private Double gyroscopeY;

    @Column(name = "gyroscopeZ")
    private Double gyroscopeZ;

}