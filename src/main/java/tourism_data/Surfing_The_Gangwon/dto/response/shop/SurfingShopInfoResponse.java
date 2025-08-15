package tourism_data.Surfing_The_Gangwon.dto.response.shop;

import java.util.List;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShopPicture;

@Builder
public record SurfingShopInfoResponse(
    String name,
    String address,
    String phone,
    String introduce,
    List<ShopImage> shopImg
) {

    @Builder
    public record ShopImage(
        String imgUrl
    ) {

        public static ShopImage create(SurfingShopPicture picture) {
            return ShopImage.builder()
                .imgUrl(picture.getImgUrl())
                .build();
        }

    }
}
