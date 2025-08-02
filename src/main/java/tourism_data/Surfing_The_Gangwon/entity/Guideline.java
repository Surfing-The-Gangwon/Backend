package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "GUIDELINE_TB")
public class Guideline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guideline_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(name = "image", nullable = true)
    private String imageUrl;

    @Column(name = "type", nullable = false)
    private String type; // type : safety, routines, wave_term
}
