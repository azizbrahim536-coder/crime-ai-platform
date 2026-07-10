package com.crimeai.backend_springboot.repository;

import com.crimeai.backend_springboot.entity.PersonneImpliquee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonneImpliqueeRepository extends JpaRepository<PersonneImpliquee, Long> {

    List<PersonneImpliquee> findByTypePersonne(String typePersonne);

    List<PersonneImpliquee> findByAffaireId(Long affaireId);
}