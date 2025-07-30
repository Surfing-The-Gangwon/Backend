package repository;

import entity.Seashore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeashoreRepository extends JpaRepository<Seashore, Long> {
    List<Seashore> findByCityId(Long cityId);
}
