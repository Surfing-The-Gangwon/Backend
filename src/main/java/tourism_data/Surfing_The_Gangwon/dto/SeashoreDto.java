package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreDto(
    Long seashoreId,
    String name,
    Integer beachCode
) {

    public static SeashoreDto create(Seashore seashore) {
        return SeashoreDto.builder()
            .seashoreId(seashore.getId())
            .name(seashore.getName())
            .beachCode(seashore.getBeachCode())
            .build();
    }
}
