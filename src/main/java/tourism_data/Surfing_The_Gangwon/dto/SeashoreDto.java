package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreDto(
    String phone,
    String name,
    String address,
    Integer beachCode
) {

    public static SeashoreDto create(Seashore seashore) {
        return SeashoreDto.builder()
            .phone(seashore.getPhone())
            .name(seashore.getName())
            .address(seashore.getAddress())
            .beachCode(seashore.getBeachCode())
            .build();
    }
}
