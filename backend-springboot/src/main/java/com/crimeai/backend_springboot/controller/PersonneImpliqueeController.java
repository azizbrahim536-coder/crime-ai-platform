package com.crimeai.backend_springboot.controller;

import com.crimeai.backend_springboot.entity.PersonneImpliquee;
import com.crimeai.backend_springboot.service.PersonneImpliqueeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonneImpliqueeController {

    private final PersonneImpliqueeService personneService;

    public PersonneImpliqueeController(PersonneImpliqueeService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public List<PersonneImpliquee> getAllPersonnes() {
        return personneService.getAllPersonnes();
    }

    @GetMapping("/{id}")
    public PersonneImpliquee getPersonneById(@PathVariable Long id) {
        return personneService.getPersonneById(id);
    }

    @PostMapping
    public PersonneImpliquee createPersonne(@RequestBody PersonneImpliquee personne) {
        return personneService.createPersonne(personne);
    }

    @PutMapping("/{id}")
    public PersonneImpliquee updatePersonne(
            @PathVariable Long id,
            @RequestBody PersonneImpliquee personne
    ) {
        return personneService.updatePersonne(id, personne);
    }

    @DeleteMapping("/{id}")
    public String deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return "Personne supprimée avec succès";
    }

    @GetMapping("/type/{typePersonne}")
    public List<PersonneImpliquee> getPersonnesByType(@PathVariable String typePersonne) {
        return personneService.getPersonnesByType(typePersonne);
    }

    @GetMapping("/affaire/{affaireId}")
    public List<PersonneImpliquee> getPersonnesByAffaire(@PathVariable Long affaireId) {
        return personneService.getPersonnesByAffaire(affaireId);
    }
}
