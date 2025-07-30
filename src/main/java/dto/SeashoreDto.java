package dto;

import entity.Seashore;
import lombok.Builder;

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
