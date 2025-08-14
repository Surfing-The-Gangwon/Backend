package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tourism_data.Surfing_The_Gangwon.entity.Marker;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    List<Marker> findBySeashoreId(Long seashoreId);
}
