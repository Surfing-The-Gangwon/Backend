package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import java.util.List;
import lombok.Builder;

@Builder
public record DailyTideFilteredResponse(
    List<DailyTideDto> tide
) {
    public static DailyTideFilteredResponse create(List<DailyTideDto> tide) {
        return DailyTideFilteredResponse.builder()
            .tide(tide)
            .build();
    }

    @Builder
    public record DailyTideDto(
        String beachNum,
        String baseDate,
        String tiStnld, // 관측지점
        String tiTime, // 간,만조 시간
        String tiType, // ET: (저)간조, FT: (고)만조
        String tilevel // 수위 cm 기준
    ) {

        public static DailyTideDto create(DailyTideResponse.Item response) {
            return DailyTideDto.builder()
                .beachNum(response.beachNum)
                .baseDate(response.baseDate)
                .tiStnld(response.tiStnld)
                .tiTime(response.tiTime)
                .tiType(response.tiType)
                .tilevel(response.tilevel)
                .build();
        }
    }
}
