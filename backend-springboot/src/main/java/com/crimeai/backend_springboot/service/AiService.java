package com.crimeai.backend_springboot.service;



import com.crimeai.backend_springboot.dto.AiChatRequest;
import com.crimeai.backend_springboot.dto.AiChatResponse;
import com.crimeai.backend_springboot.dto.CrimeClassificationRequest;
import com.crimeai.backend_springboot.dto.CrimeClassificationResponse;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final CrimeRepository crimeRepository;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public AiService(RestTemplate restTemplate, CrimeRepository crimeRepository) {
        this.restTemplate = restTemplate;
        this.crimeRepository = crimeRepository;
    }

    public AiChatResponse chat(AiChatRequest request) {
        String question = request.getQuestion() != null
                ? request.getQuestion().toLowerCase()
                : "";

        // سؤال: أكثر مدينة فيها جرائم
        if (
                (question.contains("اكثر") || question.contains("أكثر") || question.contains("plus"))
                        &&
                        (question.contains("مدينة") || question.contains("ville"))
                        &&
                        (question.contains("جرائم") || question.contains("crimes"))
        ) {
            return getMostDangerousCity();
        }

        // سؤال: عدد الجرائم
        if (
                (question.contains("عدد") || question.contains("combien") || question.contains("nombre"))
                        &&
                        (question.contains("جرائم") || question.contains("crimes"))
        ) {
            long totalCrimes = crimeRepository.count();

            return new AiChatResponse(
                    "عدد الجرائم المسجلة حاليا هو: " + totalCrimes
            );
        }

        // سؤال: أنواع الجرائم
        if (
                (question.contains("نوع") || question.contains("types"))
                        &&
                        (question.contains("جرائم") || question.contains("crimes"))
        ) {
            return getCrimesByType();
        }

        // إذا السؤال موش متعلق مباشرة بالـ database، نبعثو لـ Python
        try {
            return restTemplate.postForObject(
                    aiServiceUrl + "/ai/chat",
                    request,
                    AiChatResponse.class
            );
        } catch (Exception e) {
            return new AiChatResponse(
                    "Service IA indisponible. Vérifiez que le microservice Python est lancé."
            );
        }
    }

    private AiChatResponse getMostDangerousCity() {
        List<Object[]> results = crimeRepository.countCrimesByVille();

        if (results.isEmpty()) {
            return new AiChatResponse("ما فماش جرائم مسجلة حاليا في قاعدة البيانات.");
        }

        Optional<Object[]> maxCity = results.stream()
                .max(Comparator.comparing(row -> ((Number) row[1]).longValue()));

        if (maxCity.isEmpty()) {
            return new AiChatResponse("ما نجمش نحدد المدينة الأكثر فيها جرائم.");
        }

        String ville = maxCity.get()[0] != null ? maxCity.get()[0].toString() : "غير محددة";
        long count = ((Number) maxCity.get()[1]).longValue();

        return new AiChatResponse(
                "أكثر مدينة موجودة فيها جرائم هي: " + ville + " بعدد " + count + " جرائم."
        );
    }

    private AiChatResponse getCrimesByType() {
        List<Object[]> results = crimeRepository.countCrimesByType();

        if (results.isEmpty()) {
            return new AiChatResponse("ما فماش جرائم مسجلة حاليا.");
        }

        StringBuilder response = new StringBuilder("توزيع الجرائم حسب النوع:\n");

        for (Object[] row : results) {
            String type = row[0] != null ? row[0].toString() : "غير محدد";
            long count = ((Number) row[1]).longValue();

            response.append("- ")
                    .append(type)
                    .append(": ")
                    .append(count)
                    .append("\n");
        }

        return new AiChatResponse(response.toString());
    }

    public CrimeClassificationResponse classifyCrime(CrimeClassificationRequest request) {
        try {
            return restTemplate.postForObject(
                    aiServiceUrl + "/ai/classify",
                    request,
                    CrimeClassificationResponse.class
            );
        } catch (Exception e) {
            return new CrimeClassificationResponse("NON_DEFINI", 0.0);
        }
    }
}