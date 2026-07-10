package com.crimeai.backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "personnes_impliquees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonneImpliquee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private String genre;

    private String telephone;

    private String adresse;

    private String typePersonne;
    // SUSPECT, VICTIME, TEMOIN

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "affaire_id")
    private Affaire affaire;
}