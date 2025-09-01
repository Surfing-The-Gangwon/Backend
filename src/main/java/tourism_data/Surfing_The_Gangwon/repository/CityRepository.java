package tourism_data.Surfing_The_Gangwon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tourism_data.Surfing_The_Gangwon.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
