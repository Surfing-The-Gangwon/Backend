package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.WaterTempItemsDeserializer;

import java.util.List;

@Builder
public record WaterTempResponse(
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
        
        @JsonProperty("tm")
        public String tm; // 관측시간(년월일시분)
        
        @JsonProperty("tw")
        public String tw; // 수온
    }
}
