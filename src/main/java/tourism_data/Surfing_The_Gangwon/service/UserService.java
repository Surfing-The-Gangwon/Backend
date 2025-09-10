package tourism_data.Surfing_The_Gangwon.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.ReservedPostResponse;
import tourism_data.Surfing_The_Gangwon.dto.UserNameResponse;
import tourism_data.Surfing_The_Gangwon.dto.WrittenPostResponse;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Participant;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.GatheringRepository;
import tourism_data.Surfing_The_Gangwon.repository.ParticipantRepository;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;
import tourism_data.Surfing_The_Gangwon.status.POST_ACTION;
import tourism_data.Surfing_The_Gangwon.status.RSV_STATUS;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;
    private final ParticipantRepository participantRepository;

    public UserService(UserRepository userRepository, GatheringRepository gatheringRepository,
        ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.gatheringRepository = gatheringRepository;
        this.participantRepository = participantRepository;
    }

    public List<WrittenPostResponse> getWrittenPost(Long userId) {
        User user = getUserById(userId);
        List<Gathering> gathringList = gatheringRepository.findByWriter(user);
        List<WrittenPostResponse> responses = new ArrayList<>();

        for (Gathering gathering : gathringList) {
            POST_ACTION postAction = getPostAction(user, gathering);

            WrittenPostResponse response = WrittenPostResponse.create(gathering.getId(),
                gathering.getTitle(), gathering.getContents(), gathering.getPhone(),
                gathering.getCurrentCount(), gathering.getMaxCount(), gathering.getMeetingTime(),
                gathering.getDate(), gathering.getLevel(), gathering.getState(), postAction);
            responses.add(response);
        }

        return responses;
    }

    public List<ReservedPostResponse> getReservedPost(Long userId) {
        User user = getUserById(userId);
        List<Participant> participantList = participantRepository.findByUser(user);
        List<ReservedPostResponse> responses = new ArrayList<>();

        for (Participant participant : participantList) {
            if (participant.getRsvStatus().equals(RSV_STATUS.RESERVED)) {
                Gathering gathering = participant.getGathering();
                POST_ACTION postAction = getPostAction(user, gathering);

                ReservedPostResponse response = ReservedPostResponse.create(gathering.getId(),
                    gathering.getTitle(), gathering.getContents(), gathering.getPhone(),
                    gathering.getCurrentCount(), gathering.getMaxCount(), gathering.getMeetingTime(),
                    gathering.getDate(), gathering.getLevel(), gathering.getState(), postAction);
                responses.add(response);
            }
        }

        return responses;
    }

    public UserNameResponse getUserName(Long userId) {
        User user = getUserById(userId);

        return UserNameResponse.create(user.getUserName());
    }

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }

    private POST_ACTION getPostAction(User user, Gathering gathering) {
        POST_ACTION postAction = POST_ACTION.JOIN;

        if (gathering.getWriter().equals(user)) {
            postAction = POST_ACTION.COMPLETE;
        } else if (participantRepository.existsByUserAndGathering(user, gathering)) {
            postAction = POST_ACTION.CANCEL;
        }

        return postAction;
    }
}
