package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.FlexibleItemsDeserializer;

import java.util.List;

@Builder
public record BaseResponse<T>(
    Response<T> response
) {

    public static class Response<T> {
        public Body<T> body;
    }

    public static class Header {
        public String resultCode;
        public String resultMsg;
    }

    public static class Body<T> {
        public String dataType;
        public Items<T> items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }
    
    public static class Items<T> {
        @JsonDeserialize(using = FlexibleItemsDeserializer.class)
        public List<T> item;
    }
}
