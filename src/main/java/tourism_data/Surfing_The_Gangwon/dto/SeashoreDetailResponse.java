package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreDetailResponse(
    String name,
    String address,
    String telephone,
    Double latitude, // TODO
    Double longitude // TODO
) {

    public static SeashoreDetailResponse create(Seashore seashore) {
        return SeashoreDetailResponse.builder()
            .name(seashore.getName())
            .address(seashore.getAddress())
            .telephone(seashore.getPhone())
            .build();
    }
}
