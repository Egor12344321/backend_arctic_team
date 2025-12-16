package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "nlp_metrics")
@Builder
public class NfbMetrics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "alpha")
    private Double alpha;

    @Column(name = "beta")
    private Double beta;

    @Column(name = "theta")
    private Double theta;

    @Column(name = "delta")
    private Double delta;

    @Column(name = "smr")
    private Double smr;

}
