package tourism_data.Surfing_The_Gangwon.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.NotificationResponse;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Participant;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.GatheringRepository;
import tourism_data.Surfing_The_Gangwon.repository.ParticipantRepository;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;
import tourism_data.Surfing_The_Gangwon.status.RSV_STATUS;

@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;
    private final ParticipantRepository participantRepository;

    public NotificationService(UserRepository userRepository, GatheringRepository gatheringRepository,
        ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.gatheringRepository = gatheringRepository;
        this.participantRepository = participantRepository;
    }

    public List<NotificationResponse> getNotificationMade(Long userId) {
        User user = getUserById(userId);
        List<Gathering> gatherings = gatheringRepository.findByUser(user);
        List<NotificationResponse> responses = new ArrayList<>();

        for (Gathering gathering : gatherings) {
            List<Participant> participants = participantRepository.findByGathering(gathering);
            for (Participant participant : participants) {
                if (participant.getRsvStatus().equals(RSV_STATUS.RESERVED)) {
                    responses.add(NotificationResponse.create(participant.getUser().getUserName(),
                        gathering.getTitle()));
                }
            }
        }

        return responses;
    }

    public List<NotificationResponse> getNotificationCanceled(Long userId) {
        User user = getUserById(userId);
        List<Gathering> gatherings = gatheringRepository.findByUser(user);
        List<NotificationResponse> responses = new ArrayList<>();

        for (Gathering gathering : gatherings) {
            List<Participant> participants = participantRepository.findByGathering(gathering);
            for (Participant participant : participants) {
                if (participant.getRsvStatus().equals(RSV_STATUS.CANCELED)) {
                    responses.add(NotificationResponse.create(participant.getUser().getUserName(),
                        gathering.getTitle()));
                }
            }
        }

        return responses;
    }

    public List<NotificationResponse> getNotificationFull(Long userId) {
        User user = getUserById(userId);
        List<Gathering> gatherings = gatheringRepository.findByUser(user);
        List<NotificationResponse> responses = new ArrayList<>();

        for (Gathering gathering : gatherings) {
            if (gathering.isFull()) {
                responses.add(NotificationResponse.create(null, gathering.getTitle()));
            }
        }

        return responses;
    }

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}
