package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "productivity_metrics")
@Builder
public class ProductivityMetrics {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "gravity")
    private Double gravity;

    @Column(name = "productivity")
    private Double productivity;

    @Column(name = "fatigue")
    private Double fatigue;

    @Column(name = "reverseFatigue")
    private Double reverseFatigue;

    @Column(name = "relaxation")
    private Double relaxation;

    @Column(name = "concentration")
    private Double concentration;

}