package tourism_data.Surfing_The_Gangwon.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.GuidelineResponse;
import tourism_data.Surfing_The_Gangwon.entity.Guideline;
import tourism_data.Surfing_The_Gangwon.repository.GuidelineRepository;

@Service
public class GuidelineService {

    private final GuidelineRepository guidelineRepository;

    public GuidelineService(GuidelineRepository guidelineRepository) {
        this.guidelineRepository = guidelineRepository;
    }

    public List<GuidelineResponse> getGuidelinesByType(String type) {
        List<Guideline> guidelines = guidelineRepository.findByType(type);
        return guidelines.stream()
            .map(GuidelineResponse::create)
            .toList();
    }
}

