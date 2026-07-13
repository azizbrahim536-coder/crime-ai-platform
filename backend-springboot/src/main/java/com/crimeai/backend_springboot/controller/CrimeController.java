package com.crimeai.backend_springboot.controller;

import com.crimeai.backend_springboot.entity.Crime;
import com.crimeai.backend_springboot.service.CrimeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crimes")
@CrossOrigin(origins = "http://localhost:4200")
public class CrimeController {

    private final CrimeService crimeService;

    public CrimeController(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @GetMapping
    public List<Crime> getAllCrimes() {
        return crimeService.getAllCrimes();
    }

    @GetMapping("/search")
    public List<Crime> searchCrimes(
            @RequestParam(required = false) String typeCrime,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String statut
    ) {
        return crimeService.searchCrimes(typeCrime, ville, statut);
    }
    @GetMapping("/affaire/{affaireId}")
    public List<Crime> getCrimesByAffaire(@PathVariable Long affaireId) {
        return crimeService.getCrimesByAffaire(affaireId);
    }

    @GetMapping("/{id}")
    public Crime getCrimeById(@PathVariable Long id) {
        return crimeService.getCrimeById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENQUETEUR')")
    public Crime createCrime(@RequestBody Crime crime) {
        return crimeService.createCrime(crime);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENQUETEUR')")
    public Crime updateCrime(@PathVariable Long id, @RequestBody Crime crime) {
        return crimeService.updateCrime(id, crime);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCrime(@PathVariable Long id) {
        crimeService.deleteCrime(id);
        return "Crime supprimé avec succès";
    }

    @GetMapping("/type/{typeCrime}")
    public List<Crime> getCrimesByType(@PathVariable String typeCrime) {
        return crimeService.getCrimesByType(typeCrime);
    }

    @GetMapping("/ville/{ville}")
    public List<Crime> getCrimesByVille(@PathVariable String ville) {
        return crimeService.getCrimesByVille(ville);
    }

    @GetMapping("/statut/{statut}")
    public List<Crime> getCrimesByStatut(@PathVariable String statut) {
        return crimeService.getCrimesByStatut(statut);
    }
}