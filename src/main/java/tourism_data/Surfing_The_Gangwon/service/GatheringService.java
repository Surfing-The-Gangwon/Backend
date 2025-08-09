package tourism_data.Surfing_The_Gangwon.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.GatheringBySeashoreResponse;
import tourism_data.Surfing_The_Gangwon.dto.request.CreateGatheringRequest;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Participant;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.GatheringRepository;
import tourism_data.Surfing_The_Gangwon.repository.ParticipantRepository;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;
import tourism_data.Surfing_The_Gangwon.status.STATE;

@Service
public class GatheringService {

    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;
    private final ParticipantRepository participantRepository;
    private final SeashoreRepository seashoreRepository;

    public GatheringService(UserRepository userRepository, GatheringRepository gatheringRepository,
        ParticipantRepository participantRepository, SeashoreRepository seashoreRepository) {
        this.userRepository = userRepository;
        this.gatheringRepository = gatheringRepository;
        this.participantRepository = participantRepository;
        this.seashoreRepository = seashoreRepository;
    }

    public List<GatheringBySeashoreResponse> getGatheringBySeashoreId(LocalDate date, Long seashoreId) {
        List<Gathering> gatherings = gatheringRepository.findByDateAndSeashore_Id(date, seashoreId);
        List<GatheringBySeashoreResponse> responses = new ArrayList<>();

        for (Gathering gathering : gatherings) {
            GatheringBySeashoreResponse response = GatheringBySeashoreResponse.create(gathering.getId(),
                gathering.getTitle(), gathering.getContents(), gathering.getPhone(),
                gathering.getCurrentCount(), gathering.getMaxCount(), gathering.getMeetingTime(),
                gathering.getDate(), gathering.getLevel(), gathering.getState());
            responses.add(response);
        }

        return responses;
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

        if (gathering.isFull() || gathering.getState().equals(STATE.CLOSE)) {
            throw new IllegalStateException("모집이 마감되었습니다.");
        }
        if (participantRepository.existsByUser_IdAndGathering_Id(userId, gatheringId)) {
            throw new IllegalStateException("이미 참여한 모집글입니다.");
        }

        gathering.increaseCurrentCount();
        Participant participant = Participant.create(user, gathering);

        participantRepository.save(participant);
    }

    @Transactional
    public void cancelGathering(Long userId, Long gatheringId) {
        Gathering gathering = getGatheringById(gatheringId);

        Participant participant = participantRepository.findByUser_IdAndGathering_Id(userId, gatheringId)
            .orElseThrow(() -> new IllegalArgumentException("참여한 모집글이 아닙니다."));
        gathering.decreaseCurrentCount();

        participantRepository.delete(participant);
    }

    @Transactional
    public void closeGathering(Long userId, Long gatheringId) {
        User user = getUserById(userId);
        Gathering gathering = getGatheringById(gatheringId);

        if (user != gathering.getWriter()) {
            throw new IllegalStateException("모집글 작성자가 아닙니다.");
        }

        gathering.setStateClose();
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
