package com.example.cache_for_flutter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RecommendationService {

    private static final Logger logger = Logger.getLogger(RecommendationService.class.getName());
    private final RecommendationRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://ai.mofin.co.kr/ai/recommend_default";

    public RecommendationService(RecommendationRepository repository) {
        this.repository = repository;
        fetchAndSaveRecommendations();  // Fetch data when the service is initialized
    }

    public List<Recommendation> getAllRecommendations() {
        return repository.findAll();
    }

    public void fetchAndSaveRecommendations() {
        boolean success = fetchAndSave();
        if (!success) {
            logger.warning("Retrying... ㅇㅇㅇ");
            fetchAndSave();
        }
    }

    private boolean fetchAndSave() {
        try {
            Recommendation[] recommendations = restTemplate.getForObject(url, Recommendation[].class);
            if (recommendations != null) {
                repository.saveAll(List.of(recommendations));
                return true;
            }
        } catch (RestClientException e) {
            logger.severe(e.getMessage());
        }
        return false;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(cron = "*/5 * * * * *")
    public void scheduleFetchAndSaveRecommendations() {
        fetchAndSaveRecommendations();
    }
}
