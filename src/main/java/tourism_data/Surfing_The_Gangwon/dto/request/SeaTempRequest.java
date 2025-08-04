package tourism_data.Surfing_The_Gangwon.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SeaTempRequest extends BaseRequest {
    public static final String SEARCH_TIME = "searchTime";
    
    // 관측시간(년월일시분)  ex: 202508041320
    private String searchTime;
}
