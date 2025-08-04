package tourism_data.Surfing_The_Gangwon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BeachForecastRequest extends BaseRequest {

    public static final String BASE_DATE = "base_date";
    public static final String BASE_TIME = "base_time";

    @JsonProperty("base_date")
    private String baseDate;

    @JsonProperty("base_time")
    private String baseTime;
}
