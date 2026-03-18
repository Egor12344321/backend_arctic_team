package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.*;

import javax.print.Doc;

@Table(name = "productivity_baseline")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class ProductivityBaseline {
    @Id
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

    @Column(name = "gravity")
    private Double gravity;

    @Column(name = "productivity")
    private Double productivity;

    @Column(name = "fatigue")
    private Double fatigue;

    @Column(name = "reverse_fatigue")
    private Double reverseFatigue;

    @Column(name = "relaxation")
    private Double relaxation;

    @Column(name = "concentration")
    private Double concentration;
}


