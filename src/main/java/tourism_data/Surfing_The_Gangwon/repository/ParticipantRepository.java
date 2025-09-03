package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.Participant;
import tourism_data.Surfing_The_Gangwon.entity.User;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByUserAndGathering(User user, Gathering gathering);
    Optional<Participant> findByUserAndGathering(User user, Gathering gathering);
    List<Participant> findByUser(User user);
    List<Participant> findByGathering(Gathering gathering);
}
