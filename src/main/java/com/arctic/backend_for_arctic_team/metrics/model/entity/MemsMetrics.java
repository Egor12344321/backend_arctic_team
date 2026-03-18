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
    private Long expeditionId;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "accelerometer_x")
    private Double accelerometerX;

    @Column(name = "accelerometer_y")
    private Double accelerometerY;

    @Column(name = "accelerometer_z")
    private Double accelerometerZ;

    @Column(name = "gyroscope_x")
    private Double gyroscopeX;

    @Column(name = "gyroscope_y")
    private Double gyroscopeY;

    @Column(name = "gyroscope_z")
    private Double gyroscopeZ;

}