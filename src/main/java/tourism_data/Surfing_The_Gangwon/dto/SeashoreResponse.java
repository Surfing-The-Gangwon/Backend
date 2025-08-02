package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreResponse(
    Long id,
    String name,
    String temp, // 온도
    String seaTemp, // 수온
    String waveHeight, // 파고
    String waveSpeed, // 파도속도
    String waveDir // 파도 방향
) {

    public static SeashoreResponse create(Seashore seashore) {
        // TODO
        return SeashoreResponse.builder()
            .id(seashore.getId())
            .name(seashore.getName())
            .build();
    }
}
