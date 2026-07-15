package com.crimeai.backend_springboot.controller;

import com.crimeai.backend_springboot.dto.GraphResponseDto;
import com.crimeai.backend_springboot.service.GraphService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "http://localhost:4200")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/affaire/{id}")
    public GraphResponseDto getAffaireGraph(@PathVariable Long id) {
        return graphService.getAffaireGraph(id);
    }
}