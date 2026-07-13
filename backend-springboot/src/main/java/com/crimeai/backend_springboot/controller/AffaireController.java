package com.crimeai.backend_springboot.controller;


import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.service.AffaireService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affaires")
@CrossOrigin(origins = "http://localhost:4200")
public class AffaireController {

    private final AffaireService affaireService;

    public AffaireController(AffaireService affaireService) {
        this.affaireService = affaireService;
    }

    @GetMapping
    public List<Affaire> getAllAffaires() {
        return affaireService.getAllAffaires();
    }

    @GetMapping("/{id}")
    public Affaire getAffaireById(@PathVariable Long id) {
        return affaireService.getAffaireById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENQUETEUR')")
    public Affaire createAffaire(@RequestBody Affaire affaire) {
        return affaireService.createAffaire(affaire);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENQUETEUR')")
    public Affaire updateAffaire(@PathVariable Long id, @RequestBody Affaire affaire) {
        return affaireService.updateAffaire(id, affaire);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAffaire(@PathVariable Long id) {
        affaireService.deleteAffaire(id);
        return "Affaire supprimée avec succès";
    }

}