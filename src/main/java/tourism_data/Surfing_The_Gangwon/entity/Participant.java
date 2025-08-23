package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import tourism_data.Surfing_The_Gangwon.status.RSV_STATUS;

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

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "rev_status", nullable = false)
    private RSV_STATUS rsvStatus;

    protected Participant(){
    }

    @Builder
    private Participant(User user, Gathering gathering, RSV_STATUS rsvStatus) {
        this.user = user;
        this.gathering = gathering;
        this.rsvStatus = rsvStatus;
    }

    public static Participant create(User user, Gathering gathering, RSV_STATUS rsvStatus) {

        return Participant.builder()
            .user(user)
            .gathering(gathering)
            .rsvStatus(rsvStatus)
            .build();
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setRsvStatus(RSV_STATUS rsvStatus) {
        this.rsvStatus = rsvStatus;
    }
}
