package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

public interface SeashoreRepository extends JpaRepository<Seashore, Long> {
    List<Seashore> findByCityId(Long cityId);
}
