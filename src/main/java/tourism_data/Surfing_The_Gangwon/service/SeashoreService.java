package tourism_data.Surfing_The_Gangwon.service;

import tourism_data.Surfing_The_Gangwon.dto.SeashoreDto;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;

@Service
public class SeashoreService {
    private final SeashoreRepository seashoreRepository;

    public SeashoreService(SeashoreRepository seashoreRepository) {
        this.seashoreRepository = seashoreRepository;
    }

    public List<SeashoreDto> getSeashoresByCity(Long cityId) {
        return seashoreRepository.findByCityId(cityId)
            .stream()
            .map(SeashoreDto::create)
            .toList();
    }
}
