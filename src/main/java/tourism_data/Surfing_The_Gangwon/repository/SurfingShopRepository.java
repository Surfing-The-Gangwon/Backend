package tourism_data.Surfing_The_Gangwon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tourism_data.Surfing_The_Gangwon.entity.Marker;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShop;

public interface SurfingShopRepository extends JpaRepository<SurfingShop, Long> {
    Optional<SurfingShop> findByShopId(Long shopId);
    Optional<SurfingShop> findByMarker(Marker marker);
}
