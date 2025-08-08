package tourism_data.Surfing_The_Gangwon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

}
