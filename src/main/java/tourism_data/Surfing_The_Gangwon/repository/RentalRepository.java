package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Lesson;
import tourism_data.Surfing_The_Gangwon.entity.Rental;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShop;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByShopShopId(Long shopId);

    Long shop(SurfingShop shop);
}
