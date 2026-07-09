package com.crimeai.backend_springboot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "crimes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeCrime;
    // مثال: VOL, AGRESSION, FRAUDE, HOMICIDE

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime dateCrime;

    private String adresse;

    private String ville;

    private Double latitude;

    private Double longitude;

    private String statut;
    // مثال: SIGNALE, ENQUETE, RESOLU

    @ManyToOne
    @JoinColumn(name = "affaire_id")
    private Affaire affaire;
}