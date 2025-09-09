package tourism_data.Surfing_The_Gangwon.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tourism_data.Surfing_The_Gangwon.entity.Gathering;
import tourism_data.Surfing_The_Gangwon.entity.User;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    //List<Gathering> findByDateAndSeashore_Id(LocalDate date, Long seashoreId);
    List<Gathering> findByWriter(User writer);

    @Query("""
    SELECT g
    FROM Gathering g
    WHERE g.seashore.id = :seashoreId
      AND g.meetingTime >= :start
      AND g.meetingTime < :end
    """)
    List<Gathering> findAllBySeashoreAndMeetingDate(
        @Param("seashoreId") Long seashoreId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
