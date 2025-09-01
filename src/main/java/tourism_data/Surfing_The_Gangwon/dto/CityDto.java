package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.City;

@Builder
public record CityDto(
    Long cityId,
    String cityName,
    String areaCode
) {

    public static CityDto create(City city) {
        return CityDto.builder()
            .cityId(city.getId())
            .cityName(city.getCityName())
            .areaCode(city.getAreaCode())
            .build();
    }
}
