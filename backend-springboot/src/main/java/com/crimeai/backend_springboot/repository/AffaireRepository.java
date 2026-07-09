package com.crimeai.backend_springboot.repository;

import com.crimeai.backend_springboot.entity.Affaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffaireRepository extends JpaRepository<Affaire, Long> {
}