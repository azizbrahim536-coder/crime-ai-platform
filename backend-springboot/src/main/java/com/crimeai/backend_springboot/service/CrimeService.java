package com.crimeai.backend_springboot.service;


import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.entity.Crime;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrimeService {

    private final CrimeRepository crimeRepository;
    private final AffaireRepository affaireRepository;

    public CrimeService(CrimeRepository crimeRepository, AffaireRepository affaireRepository) {
        this.crimeRepository = crimeRepository;
        this.affaireRepository = affaireRepository;
    }

    public List<Crime> getAllCrimes() {
        return crimeRepository.findAll();
    }

    public Crime getCrimeById(Long id) {
        return crimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crime introuvable avec id: " + id));
    }

    public Crime createCrime(Crime crime) {
        if (crime.getStatut() == null || crime.getStatut().isEmpty()) {
            crime.setStatut("SIGNALE");
        }

        if (crime.getAffaire() != null && crime.getAffaire().getId() != null) {
            Affaire affaire = affaireRepository.findById(crime.getAffaire().getId())
                    .orElseThrow(() -> new RuntimeException("Affaire introuvable"));
            crime.setAffaire(affaire);
        }

        return crimeRepository.save(crime);
    }

    public Crime updateCrime(Long id, Crime newCrime) {
        Crime crime = getCrimeById(id);

        crime.setTypeCrime(newCrime.getTypeCrime());
        crime.setDescription(newCrime.getDescription());
        crime.setDateCrime(newCrime.getDateCrime());
        crime.setAdresse(newCrime.getAdresse());
        crime.setVille(newCrime.getVille());
        crime.setLatitude(newCrime.getLatitude());
        crime.setLongitude(newCrime.getLongitude());
        crime.setStatut(newCrime.getStatut());

        if (newCrime.getAffaire() != null && newCrime.getAffaire().getId() != null) {
            Affaire affaire = affaireRepository.findById(newCrime.getAffaire().getId())
                    .orElseThrow(() -> new RuntimeException("Affaire introuvable"));
            crime.setAffaire(affaire);
        }

        return crimeRepository.save(crime);
    }

    public void deleteCrime(Long id) {
        Crime crime = getCrimeById(id);
        crimeRepository.delete(crime);
    }

    public List<Crime> getCrimesByType(String typeCrime) {
        return crimeRepository.findByTypeCrime(typeCrime);
    }

    public List<Crime> getCrimesByVille(String ville) {
        return crimeRepository.findByVille(ville);
    }

    public List<Crime> getCrimesByStatut(String statut) {
        return crimeRepository.findByStatut(statut);
    }
    public List<Crime> searchCrimes(String typeCrime, String ville, String statut) {
        return crimeRepository.searchCrimes(typeCrime, ville, statut);
    }
    public List<Crime> getCrimesByAffaire(Long affaireId) {
        return crimeRepository.findByAffaireId(affaireId);
    }
}