package tourism_data.Surfing_The_Gangwon.service;

import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.request.CreateGatheringRequest;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.GatheringRepository;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;
import tourism_data.Surfing_The_Gangwon.status.STATE;

@Service
public class GatheringService {

    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;
    private final SeashoreRepository seashoreRepository;

    public GatheringService(UserRepository userRepository, GatheringRepository gatheringRepository,
        SeashoreRepository seashoreRepository) {
        this.userRepository = userRepository;
        this.gatheringRepository = gatheringRepository;
        this.seashoreRepository = seashoreRepository;
    }

    public void createGathering(Long userId, CreateGatheringRequest request) {
        Seashore seashore = seashoreRepository.findByName(request.seashoreName())
            .orElseThrow(() -> new IllegalArgumentException("not found seashore"));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("not found user"));

        Gathering gathering = Gathering.create(user, seashore, request.title(), request.contents(),
            request.phone(), request.maxCount(), request.meetingTime(), request.level(), STATE.OPEN);
        gathering.setDate();

        gatheringRepository.save(gathering);
    }
}
