package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GATHERING_USER_TB")
@Getter
@NoArgsConstructor
public class GatheringUser {

    @EmbeddedId
    private GatheringUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gatheringId")
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public GatheringUser(Gathering gathering, User user) {
        this.gathering = gathering;
        this.user = user;
        this.id = new GatheringUserId(gathering.getId(), user.getId());
    }
}
