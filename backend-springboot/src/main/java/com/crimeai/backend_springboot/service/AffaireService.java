package com.crimeai.backend_springboot.service;



import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AffaireService {

    private final AffaireRepository affaireRepository;

    public AffaireService(AffaireRepository affaireRepository) {
        this.affaireRepository = affaireRepository;
    }

    public List<Affaire> getAllAffaires() {
        return affaireRepository.findAll();
    }

    public Affaire getAffaireById(Long id) {
        return affaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affaire introuvable avec id: " + id));
    }

    public Affaire createAffaire(Affaire affaire) {
        affaire.setDateCreation(LocalDateTime.now());

        if (affaire.getStatut() == null || affaire.getStatut().isEmpty()) {
            affaire.setStatut("OUVERTE");
        }

        return affaireRepository.save(affaire);
    }

    public Affaire updateAffaire(Long id, Affaire newAffaire) {
        Affaire affaire = getAffaireById(id);

        affaire.setNumeroAffaire(newAffaire.getNumeroAffaire());
        affaire.setTitre(newAffaire.getTitre());
        affaire.setDescription(newAffaire.getDescription());
        affaire.setStatut(newAffaire.getStatut());

        return affaireRepository.save(affaire);
    }

    public void deleteAffaire(Long id) {
        Affaire affaire = getAffaireById(id);
        affaireRepository.delete(affaire);
    }
}