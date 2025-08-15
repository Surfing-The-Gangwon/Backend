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
@Table(name = "SURFING_SHOP_PICTURE_TB")
public class SurfingShopPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id", nullable = false)
    private Long pictureId;

    @Column(name = "imgUrl")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private SurfingShop shop;

    public SurfingShopPicture(String imgUrl, SurfingShop shop) {
        this.imgUrl = imgUrl;
        this.shop = shop;
    }

    public SurfingShopPicture() {}
}
