package tourism_data.Surfing_The_Gangwon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShopPicture;

import java.util.List;

@Repository
public interface SurfingShopPictureRepository extends JpaRepository<SurfingShopPicture, Long> {
    List<SurfingShopPicture> findByShopShopId(Long shopId);
}