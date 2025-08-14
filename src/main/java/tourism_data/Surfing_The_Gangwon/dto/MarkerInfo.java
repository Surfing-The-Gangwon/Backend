package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;

@Builder
public record MarkerInfo(
    String type,
    Double latitude,
    Double longitude,
    String name
) {


}
