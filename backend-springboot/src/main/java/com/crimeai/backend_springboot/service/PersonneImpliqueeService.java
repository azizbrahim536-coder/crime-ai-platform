package com.crimeai.backend_springboot.service;

import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.entity.PersonneImpliquee;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.PersonneImpliqueeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneImpliqueeService {

    private final PersonneImpliqueeRepository personneRepository;
    private final AffaireRepository affaireRepository;

    public PersonneImpliqueeService(
            PersonneImpliqueeRepository personneRepository,
            AffaireRepository affaireRepository
    ) {
        this.personneRepository = personneRepository;
        this.affaireRepository = affaireRepository;
    }

    public List<PersonneImpliquee> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public PersonneImpliquee getPersonneById(Long id) {
        return personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne introuvable avec id: " + id));
    }

    public PersonneImpliquee createPersonne(PersonneImpliquee personne) {
        if (personne.getAffaire() != null && personne.getAffaire().getId() != null) {
            Affaire affaire = affaireRepository.findById(personne.getAffaire().getId())
                    .orElseThrow(() -> new RuntimeException("Affaire introuvable"));

            personne.setAffaire(affaire);
        }

        return personneRepository.save(personne);
    }

    public PersonneImpliquee updatePersonne(Long id, PersonneImpliquee newPersonne) {
        PersonneImpliquee personne = getPersonneById(id);

        personne.setNom(newPersonne.getNom());
        personne.setPrenom(newPersonne.getPrenom());
        personne.setDateNaissance(newPersonne.getDateNaissance());
        personne.setGenre(newPersonne.getGenre());
        personne.setTelephone(newPersonne.getTelephone());
        personne.setAdresse(newPersonne.getAdresse());
        personne.setTypePersonne(newPersonne.getTypePersonne());
        personne.setNotes(newPersonne.getNotes());

        if (newPersonne.getAffaire() != null && newPersonne.getAffaire().getId() != null) {
            Affaire affaire = affaireRepository.findById(newPersonne.getAffaire().getId())
                    .orElseThrow(() -> new RuntimeException("Affaire introuvable"));

            personne.setAffaire(affaire);
        } else {
            personne.setAffaire(null);
        }

        return personneRepository.save(personne);
    }

    public void deletePersonne(Long id) {
        PersonneImpliquee personne = getPersonneById(id);
        personneRepository.delete(personne);
    }

    public List<PersonneImpliquee> getPersonnesByType(String typePersonne) {
        return personneRepository.findByTypePersonne(typePersonne);
    }

    public List<PersonneImpliquee> getPersonnesByAffaire(Long affaireId) {
        return personneRepository.findByAffaireId(affaireId);
    }
}