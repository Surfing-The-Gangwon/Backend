package tourism_data.Surfing_The_Gangwon.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.User;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    List<Gathering> findByDateAndSeashore_Id(LocalDate date, Long seashoreId);
    List<Gathering> findByWriter(User writer);
}
