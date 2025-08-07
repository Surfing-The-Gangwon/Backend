package tourism_data.Surfing_The_Gangwon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tourism_data.Surfing_The_Gangwon.util.ApiKeyManager;
import tourism_data.Surfing_The_Gangwon.util.ApiKeyManager.ApiKeyType;

@Getter
@SuperBuilder
public class BaseRequest {

    public static final String SERVICE_KEY = "serviceKey";
    public static final String PAGE_NO = "pageNo";
    public static final String NUM_OF_ROWS = "numOfRows";
    public static final String DATA_TYPE = "dataType";
    public static final String BEACH_NUM = "beach_num";

    @Builder.Default
    private String serviceKey = getServiceKey();
    @Builder.Default
    private String numOfRows = "10";
    @Builder.Default
    private String pageNo = "1";
    @Builder.Default
    private String dataType = "JSON";

    @JsonProperty("beach_num")
    private String beachNum;

    public static String getServiceKey() {
        return ApiKeyManager.getApiKey(ApiKeyType.WEATHER_API);
    }
}
