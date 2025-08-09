package tourism_data.Surfing_The_Gangwon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByUser_IdAndGathering_Id(Long userId, Long gatheringId);
    Optional<Participant> findByUser_IdAndGathering_Id(Long userId, Long gatheringId);
}
