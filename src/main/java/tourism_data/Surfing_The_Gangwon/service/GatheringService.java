package tourism_data.Surfing_The_Gangwon.service;

import jakarta.transaction.Transactional;
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
        User user = getUserById(userId);
        Seashore seashore = seashoreRepository.findByName(request.seashoreName())
            .orElseThrow(() -> new IllegalArgumentException("not found seashore"));

        Gathering gathering = Gathering.create(user, seashore, request.title(), request.contents(),
            request.phone(), request.maxCount(), request.meetingTime(), request.level(), STATE.OPEN);
        gathering.setDate();

        gatheringRepository.save(gathering);
    }

    @Transactional
    public void joinGathering(Long userId, Long gatheringId) {
        User user = getUserById(userId);
        Gathering gathering = getGatheringById(gatheringId);

        if (gathering.getCurrentCount() >= gathering.getMaxCount()) {
            throw new IllegalStateException("모집이 마감되었습니다.");
        }

        boolean alreadyJoined = gathering.getParticipants().stream()
            .anyMatch(gu -> gu.getUser().equals(user));
        if (alreadyJoined) {
            throw new IllegalStateException("이미 참여한 모집글입니다.");
        }

        gathering.addParticipant(user);
        gatheringRepository.save(gathering);
    }

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }

    private Gathering getGatheringById(Long gatheringId) {

        return gatheringRepository.findById(gatheringId)
            .orElseThrow(() -> new IllegalArgumentException("not found gathering"));
    }
}
