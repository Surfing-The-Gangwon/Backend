package service;

import dto.SeashoreDto;
import java.util.List;
import org.springframework.stereotype.Service;
import repository.SeashoreRepository;

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
