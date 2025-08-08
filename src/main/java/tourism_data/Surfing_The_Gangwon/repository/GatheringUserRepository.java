package tourism_data.Surfing_The_Gangwon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.GatheringUser;
import tourism_data.Surfing_The_Gangwon.entity.GatheringUserId;

@Repository
public interface GatheringUserRepository extends JpaRepository<GatheringUser, GatheringUserId> {

}
