package com.crimeai.backend_springboot.repository;


import com.crimeai.backend_springboot.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrimeRepository extends JpaRepository<Crime, Long> {

    List<Crime> findByTypeCrime(String typeCrime);

    List<Crime> findByVille(String ville);

    List<Crime> findByStatut(String statut);
}