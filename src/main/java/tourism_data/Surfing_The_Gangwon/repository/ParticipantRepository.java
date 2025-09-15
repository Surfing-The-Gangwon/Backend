package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Participant;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.status.RSV_STATUS;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByUserAndGathering(User user, Gathering gathering);
    Optional<Participant> findFirstByUser_IdAndGathering_IdOrderByIdDesc(Long userId, Long gatheringId);
    boolean existsByUserAndGatheringAndRsvStatus(User user, Gathering gathering, RSV_STATUS rsvStatus);

    List<Participant> findByUser(User user);

    List<Participant> findByGathering(Gathering gathering);
}
