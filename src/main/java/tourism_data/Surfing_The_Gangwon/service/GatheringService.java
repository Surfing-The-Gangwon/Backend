package tourism_data.Surfing_The_Gangwon.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import tourism_data.Surfing_The_Gangwon.status.RSV_STATUS;
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
        System.out.println("0");
        User user = getUserById(userId);
        System.out.println("1");
        Seashore seashore = seashoreRepository.findByName(request.seashoreName())
            .orElseThrow(() -> new IllegalArgumentException("not found seashore"));
        System.out.println("2");
        Gathering gathering = Gathering.create(user, seashore, request.title(), request.contents(),
            request.phone(), request.maxCount(), request.level(), STATE.OPEN);
        System.out.println("3");
        gathering.setDate();
        gathering.setMeetingTime();
        System.out.println("4");
        gatheringRepository.save(gathering);
        System.out.println("5");
    }

    @Transactional
    public void joinGathering(Long userId, Long gatheringId) {
        User user = getUserById(userId);
        Gathering gathering = getGatheringById(gatheringId);

        if (gathering.getState() == STATE.CLOSE) {
            throw new IllegalStateException("모집이 마감되었습니다.");
        }

        Optional<Participant> existingOpt = participantRepository.findByUserAndGathering(user, gathering);

        if (existingOpt.isPresent()) {
            Participant participant = existingOpt.get();

            if (participant.getRsvStatus() == RSV_STATUS.CANCELED) {
                if (gathering.isFull()) {
                    throw new IllegalStateException("모집이 마감되었습니다.");
                }
                participant.setRsvStatus(RSV_STATUS.RESERVED);
                participant.setDate();
                gathering.increaseCurrentCount();

                participantRepository.save(participant);
            } else {
                throw new IllegalStateException("이미 참여한 모집글입니다.");
            }

        } else {
            if (gathering.isFull()) {
                throw new IllegalStateException("모집이 마감되었습니다.");
            }

            Participant participant = Participant.create(user, gathering, RSV_STATUS.RESERVED);
            participant.setDate();
            participantRepository.save(participant);
            gathering.increaseCurrentCount();
        }
    }

    @Transactional
    public void cancelGathering(Long userId, Long gatheringId) {
        User user = getUserById(userId);
        Gathering gathering = getGatheringById(gatheringId);

        Participant participant = participantRepository.findByUserAndGathering(user, gathering)
            .orElseThrow(() -> new IllegalArgumentException("참여한 모집글이 아닙니다."));
        gathering.decreaseCurrentCount();

        participant.setRsvStatus(RSV_STATUS.CANCELED);
        participant.setDate();

        participantRepository.save(participant);
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

    public void deleteGathering(Long userId, Long gatheringId) {
        User user = getUserById(userId);
        Gathering gathering = getGatheringById(gatheringId);

        if (user != gathering.getWriter()) {
            throw new IllegalStateException("모집글 작성자가 아닙니다.");
        }

        gatheringRepository.delete(gathering);
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
