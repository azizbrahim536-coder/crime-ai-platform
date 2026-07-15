package com.crimeai.backend_springboot.service;

import com.crimeai.backend_springboot.dto.GraphEdgeDto;
import com.crimeai.backend_springboot.dto.GraphNodeDto;
import com.crimeai.backend_springboot.dto.GraphResponseDto;
import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.entity.Crime;
import com.crimeai.backend_springboot.entity.PersonneImpliquee;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import com.crimeai.backend_springboot.repository.PersonneImpliqueeRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphService {

    private final AffaireRepository affaireRepository;
    private final CrimeRepository crimeRepository;
    private final PersonneImpliqueeRepository personneRepository;

    public GraphService(
            AffaireRepository affaireRepository,
            CrimeRepository crimeRepository,
            PersonneImpliqueeRepository personneRepository
    ) {
        this.affaireRepository = affaireRepository;
        this.crimeRepository = crimeRepository;
        this.personneRepository = personneRepository;
    }

    public GraphResponseDto getAffaireGraph(Long affaireId) {
        Affaire affaire = affaireRepository.findById(affaireId)
                .orElseThrow(() -> new RuntimeException("Affaire introuvable"));

        List<Crime> crimes = crimeRepository.findByAffaireId(affaireId);
        List<PersonneImpliquee> personnes = personneRepository.findByAffaireId(affaireId);

        List<GraphNodeDto> nodes = new ArrayList<>();
        List<GraphEdgeDto> edges = new ArrayList<>();

        String affaireNodeId = "affaire-" + affaire.getId();

        nodes.add(new GraphNodeDto(
                affaireNodeId,
                affaire.getNumeroAffaire() + " - " + affaire.getTitre(),
                "AFFAIRE"
        ));

        for (Crime crime : crimes) {
            String crimeNodeId = "crime-" + crime.getId();

            nodes.add(new GraphNodeDto(
                    crimeNodeId,
                    crime.getTypeCrime(),
                    "CRIME"
            ));

            edges.add(new GraphEdgeDto(
                    affaireNodeId,
                    crimeNodeId,
                    "contient"
            ));

            if (crime.getVille() != null && !crime.getVille().isEmpty()) {
                String villeNodeId = "ville-" + crime.getVille();

                boolean villeExists = nodes.stream()
                        .anyMatch(node -> node.getId().equals(villeNodeId));

                if (!villeExists) {
                    nodes.add(new GraphNodeDto(
                            villeNodeId,
                            crime.getVille(),
                            "VILLE"
                    ));
                }

                edges.add(new GraphEdgeDto(
                        crimeNodeId,
                        villeNodeId,
                        "situé à"
                ));
            }
        }

        for (PersonneImpliquee personne : personnes) {
            String personneNodeId = "personne-" + personne.getId();

            nodes.add(new GraphNodeDto(
                    personneNodeId,
                    personne.getNom() + " " + personne.getPrenom(),
                    personne.getTypePersonne()
            ));

            edges.add(new GraphEdgeDto(
                    affaireNodeId,
                    personneNodeId,
                    personne.getTypePersonne()
            ));
        }

        return new GraphResponseDto(nodes, edges);
    }
}