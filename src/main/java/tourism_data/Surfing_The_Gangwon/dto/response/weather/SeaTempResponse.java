package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import java.util.List;
import lombok.Builder;

@Builder
public record SeaTempResponse(
    Response response
) {

    public static class Response {
        public Header header;
        public Body body;
    }

    public static class Header {
        public String resultCode;
        public String resultMsg;
    }

    public static class Body {
        public String dataType;
        public List<Item> items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }

    public static class Item {
        public String beachNum;
        public String tm; // 관측시간(년월일시분)
        public String tw; // 수온
    }
}
