package tourism_data.Surfing_The_Gangwon.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UVRequest extends BaseRequest {
    public static final String AREA_NO = "areaNo";
    public static final String TIME = "time";

    // 행정구역 코드
    private String areaNo;

    // 발표시간(년월일시분)  ex: 202508041320
    private String time;
}