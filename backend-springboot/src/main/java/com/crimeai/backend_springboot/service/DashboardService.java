package com.crimeai.backend_springboot.service;


import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final CrimeRepository crimeRepository;
    private final AffaireRepository affaireRepository;

    public DashboardService(CrimeRepository crimeRepository, AffaireRepository affaireRepository) {
        this.crimeRepository = crimeRepository;
        this.affaireRepository = affaireRepository;
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalCrimes", crimeRepository.count());
        stats.put("totalAffaires", affaireRepository.count());

        stats.put("crimesParType", convertToMap(crimeRepository.countCrimesByType()));
        stats.put("crimesParVille", convertToMap(crimeRepository.countCrimesByVille()));
        stats.put("crimesParStatut", convertToMap(crimeRepository.countCrimesByStatut()));
        stats.put("affairesParStatut", convertToMap(affaireRepository.countAffairesByStatut()));

        return stats;
    }

    private Map<String, Long> convertToMap(List<Object[]> results) {
        Map<String, Long> map = new HashMap<>();

        for (Object[] row : results) {
            String key = row[0] != null ? row[0].toString() : "Non défini";
            Long value = (Long) row[1];
            map.put(key, value);
        }

        return map;
    }
}