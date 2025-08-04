package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.FlexibleItemsDeserializer;

@Builder
public record WaterTempResponse(
    Response response
) {

    public static class Response {
//        public Header header;
        public Body body;
    }

//    public static class Header {
//        public String resultCode;
//        public String resultMsg;
//    }

    public static class Body {
//        public String dataType;
        public Items items;
//        public int pageNo;
//        public int numOfRows;
//        public int totalCount;
    }
    
    public static class Items {
        @JsonDeserialize(using = FlexibleItemsDeserializer.class)
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
