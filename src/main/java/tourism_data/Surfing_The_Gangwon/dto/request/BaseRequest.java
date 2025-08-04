package tourism_data.Surfing_The_Gangwon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseRequest {

    public static final String SERVICE_KEY = "serviceKey";
    public static final String PAGE_NO = "pageNo";
    public static final String NUM_OF_ROWS = "numOfRows";
    public static final String DATA_TYPE = "dataType";
    public static final String BEACH_NUM = "beach_num";

    @Builder.Default
    private String serviceKey = getServiceKeyFromProperties();
    @Builder.Default
    private String numOfRows = "10";
    @Builder.Default
    private String pageNo = "1";
    @Builder.Default
    private String dataType = "JSON";

    @JsonProperty("beach_num")
    private String beachNum;

    private static String getServiceKeyFromProperties() {
        // 환경변수에서 API 키 가져오기
        return System.getenv("WEATHER_API_KEY");
    }
}
