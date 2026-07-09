package com.crimeai.backend_springboot.repository;

import com.crimeai.backend_springboot.entity.Affaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AffaireRepository extends JpaRepository<Affaire, Long> {

    @Query("SELECT a.statut, COUNT(a) FROM Affaire a GROUP BY a.statut")
    List<Object[]> countAffairesByStatut();
}