package tourism_data.Surfing_The_Gangwon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tourism_data.Surfing_The_Gangwon.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByShopShopId(Long shopId);
}
