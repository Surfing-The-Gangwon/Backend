package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "SEASHORE_TB")
public class Seashore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seashore_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "beach_code", nullable = false)
    private Integer beachCode;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
