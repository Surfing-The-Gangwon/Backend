package tourism_data.Surfing_The_Gangwon.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.WrittenPostResponse;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.GatheringRepository;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;

    public UserService(UserRepository userRepository, GatheringRepository gatheringRepository) {
        this.userRepository = userRepository;
        this.gatheringRepository = gatheringRepository;
    }

    public List<WrittenPostResponse> getWrittenPost(Long userId) {
        User user = getUserById(userId);
        List<Gathering> gathringList = gatheringRepository.findByUser(user);
        List<WrittenPostResponse> responses = new ArrayList<>();

        for (Gathering gathering : gathringList) {
            WrittenPostResponse response = WrittenPostResponse.create(gathering.getId(),
                gathering.getTitle(), gathering.getContents(), gathering.getPhone(),
                gathering.getCurrentCount(), gathering.getMaxCount(), gathering.getMeetingTime(),
                gathering.getDate(), gathering.getLevel(), gathering.getState());
            responses.add(response);
        }

        return responses;
    }

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}
