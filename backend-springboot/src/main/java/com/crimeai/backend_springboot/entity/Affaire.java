package com.crimeai.backend_springboot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "affaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroAffaire;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String statut;
    // مثال: OUVERTE, EN_COURS, CLOTUREE

    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "affaire", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Crime> crimes;
}