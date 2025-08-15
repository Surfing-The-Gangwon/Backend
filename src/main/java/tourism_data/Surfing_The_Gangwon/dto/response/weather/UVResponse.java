package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.UVItemsDeserializer;
import tourism_data.Surfing_The_Gangwon.util.WaterTempItemsDeserializer;

@Builder
public record UVResponse(
    Response response
) {
    public static class Response extends BaseResponse.Response<UVResponse> {
        public UVResponse.Body body;
    }

    public static class Body extends BaseResponse.Body<UVResponse> {
        public UVResponse.Items items;
    }

    public static class Items extends BaseResponse.Items<UVResponse> {
        @JsonDeserialize(using = UVItemsDeserializer.class)
        public List<UVResponse.Item> item;
    }

    public static class Item {
        @JsonProperty("h0")
        public String uvResult;
    }
}
