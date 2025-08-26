package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.WaterTempItemsDeserializer;

@Builder
public record DailyTideResponse(
    Response response
) {
    public static class Response extends BaseResponse.Response<WaterTempResponse> {
        public Body body;
    }

    public static class Body extends BaseResponse.Body<WaterTempResponse> {
        public Items items;
    }

    public static class Items extends BaseResponse.Items<WaterTempResponse> {
        @JsonDeserialize(using = WaterTempItemsDeserializer.class)
        public List<Item> item;
    }

    public static class Item {
        @JsonProperty("beachNum")
        public String beachNum;

        @JsonProperty("tiStnld")
        public String tiStnld; // 관측지점

        @JsonProperty("tiTime")
        public String tiTime; // 간,만조 시간

        @JsonProperty("tiType")
        public String tiType; // ET: (저)간조, FT: (고)만조

        @JsonProperty("tilevel")
        public String tilevel; // 수위 cm 기준
    }
}
