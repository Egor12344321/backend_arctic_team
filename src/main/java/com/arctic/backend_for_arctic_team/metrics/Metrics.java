package com.arctic.backend_for_arctic_team.metrics;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "metrics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "individual_number", nullable = false)
    private String individualNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expedition_id")
    private Expedition expedition;

    @Column(name = "measured_time", nullable = false)
    private LocalDateTime measuredTime;

    // Когнитивные показатели
    @Column(name = "cognitive_score")
    private Double cognitiveScore;

    private Integer focus;

    @Column(name = "concentration_index")
    private Double concentrationIndex;

    // Эмоциональные состояния
    private Integer chill;
    private Integer stress;

    @Column(name = "self_control")
    private Integer selfControl;

    private Integer anger;

    @Column(name = "relaxation_index")
    private Double relaxationIndex;

    // Физиологические показатели
    @Column(name = "fatigue_score")
    private Double fatigueScore;

    @Column(name = "reverse_fatigue")
    private Double reverseFatigue;

    @Column(name = "alpha_gravity")
    private Double alphaGravity;

    @Column(name = "heart_rate")
    private Double heartRate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
