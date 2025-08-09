package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "PARTICIPANT_TB")
@Getter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gathering_id", nullable = false)
    private Gathering gathering;

    protected Participant(){
    }

    @Builder
    private Participant(User user, Gathering gathering) {
        this.user = user;
        this.gathering = gathering;
    }

    public static Participant create(User user, Gathering gathering) {

        return Participant.builder()
            .user(user)
            .gathering(gathering)
            .build();
    }
}
