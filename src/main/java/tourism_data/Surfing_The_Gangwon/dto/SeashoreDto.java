package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreDto(
    String name,
    String temp, // 온도
    String seaTemp, // 수온
    String waveHeight, // 파고
    String waveSpeed, // 파도속도
    String waveDir // 파도 방향
) {

    public static SeashoreDto create(Seashore seashore) {
        return SeashoreDto.builder()
            .name(seashore.getName())
            .build();
    }
}
