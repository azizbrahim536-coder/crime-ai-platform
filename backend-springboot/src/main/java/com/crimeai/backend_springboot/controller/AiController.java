package com.crimeai.backend_springboot.controller;

import com.crimeai.backend_springboot.dto.AiChatRequest;
import com.crimeai.backend_springboot.dto.AiChatResponse;
import com.crimeai.backend_springboot.dto.CrimeClassificationRequest;
import com.crimeai.backend_springboot.dto.CrimeClassificationResponse;
import com.crimeai.backend_springboot.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public AiChatResponse chat(@RequestBody AiChatRequest request) {
        return aiService.chat(request);
    }

    @PostMapping("/classify")
    public CrimeClassificationResponse classifyCrime(@RequestBody CrimeClassificationRequest request) {
        return aiService.classifyCrime(request);
    }
}