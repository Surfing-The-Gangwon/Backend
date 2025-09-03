package tourism_data.Surfing_The_Gangwon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import tourism_data.Surfing_The_Gangwon.status.LEVEL;
import tourism_data.Surfing_The_Gangwon.status.POST_ACTION;
import tourism_data.Surfing_The_Gangwon.status.STATE;

@Entity
@Table(name = "GATHERING_TB")
@Getter
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "seashore_id", nullable = false)
    private Seashore seashore;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "currentCount", nullable = false)
    private int currentCount;

    @Column(name = "maxCount", nullable = false)
    private int maxCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/seoul")
    @Column(name = "meetingTime", nullable = false)
    private LocalDateTime meetingTime;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private LEVEL level;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private STATE state;

    protected Gathering() {
    }

    @Builder
    private Gathering(User writer, Seashore seashore, String title, String contents, String phone,
        int maxCount, LocalDateTime meetingTime, LEVEL level, STATE state) {
        this.writer = writer;
        this.seashore = seashore;
        this.title = title;
        this.contents = contents;
        this.phone = phone;
        this.maxCount = maxCount;
        this.meetingTime = meetingTime;
        this.level = level;
        this.state = state;
    }

    public static Gathering create(User writer, Seashore seashore, String title, String contents, String phone,
        int maxCount, LEVEL level, STATE state) {

        return Gathering.builder()
            .writer(writer)
            .seashore(seashore)
            .title(title)
            .contents(contents)
            .phone(phone)
            .maxCount(maxCount)
            .level(level)
            .state(state)
            .build();
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setStateClose() {
        this.state = STATE.CLOSE;
    }

    public void increaseCurrentCount() {
        this.currentCount += 1;
    }

    public void decreaseCurrentCount() {
        this.currentCount -= 1;
    }

    public boolean isFull() {
        return currentCount >= maxCount;
    }
}
