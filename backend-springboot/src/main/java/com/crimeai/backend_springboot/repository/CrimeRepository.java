package com.crimeai.backend_springboot.repository;


import com.crimeai.backend_springboot.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrimeRepository extends JpaRepository<Crime, Long> {

    List<Crime> findByTypeCrime(String typeCrime);

    List<Crime> findByVille(String ville);

    List<Crime> findByStatut(String statut);
    List<Crime> findByAffaireId(Long affaireId);

    @Query("""
        SELECT c FROM Crime c
        WHERE (:typeCrime IS NULL OR :typeCrime = '' OR c.typeCrime = :typeCrime)
        AND (:ville IS NULL OR :ville = '' OR LOWER(c.ville) LIKE LOWER(CONCAT('%', :ville, '%')))
        AND (:statut IS NULL OR :statut = '' OR c.statut = :statut)
    """)
    List<Crime> searchCrimes(
            @Param("typeCrime") String typeCrime,
            @Param("ville") String ville,
            @Param("statut") String statut
    );

    @Query("SELECT c.typeCrime, COUNT(c) FROM Crime c GROUP BY c.typeCrime")
    List<Object[]> countCrimesByType();

    @Query("SELECT c.ville, COUNT(c) FROM Crime c GROUP BY c.ville")
    List<Object[]> countCrimesByVille();

    @Query("SELECT c.statut, COUNT(c) FROM Crime c GROUP BY c.statut")
    List<Object[]> countCrimesByStatut();
}