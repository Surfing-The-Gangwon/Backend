package tourism_data.Surfing_The_Gangwon.service;

import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;

@Service
public class SeashoreService {
    private final SeashoreRepository seashoreRepository;

    public SeashoreService(SeashoreRepository seashoreRepository) {
        this.seashoreRepository = seashoreRepository;
    }

    public List<SeashoreResponse> getSeashoresByCity(Long cityId) {
        return seashoreRepository.findByCityId(cityId)
            .stream()
            .map(SeashoreResponse::create)
            .toList();
    }

//    public SeashoreDetailDto getSeashoreById(Long seashoreId) {
//        Seashore seashoreEntity = seashoreRepository.findBySeashoreId(seashoreId)
//            .orElseThrow(() -> new RuntimeException("seashore not found"));
//        return SeashoreDetailDto.create(seashoreEntity);
//    }
}
