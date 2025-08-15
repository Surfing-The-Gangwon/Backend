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

@Getter
@Entity
@Table(name = "SURFING_SHOP_TB")
public class SurfingShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "introduce", columnDefinition = "TEXT")
    private String introduce;

    @ManyToOne
    @JoinColumn(name = "marker_id", nullable = false)
    private Marker marker;
}
