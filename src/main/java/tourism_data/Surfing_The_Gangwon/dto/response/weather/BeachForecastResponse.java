package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.BeachForecastItemsDeserializer;

@Builder
public record BeachForecastResponse(
    Response response
) {
    public static class Response extends BaseResponse.Response<BeachForecastResponse> {
        public Body body;
    }

    public static class Body extends BaseResponse.Body<BeachForecastResponse> {
        public Items items;
    }

    public static class Items extends BaseResponse.Items<BeachForecastResponse> {
        @JsonDeserialize(using = BeachForecastItemsDeserializer.class)
        public List<Item> item;
    }

    public static class Item {
        @JsonProperty("beachNum")
        public String beachNum;

        /**
         * SKY(하늘상태) : 맑음(1), 구름없음(3), 흐림(4)
         * PTY(강수형태) : 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
         *
         * TMP(기온), WAV(파고), VEC(풍향), WSD(풍속)
         */
        @JsonProperty("category")
        public String category; // 카테고리 코드값 정보

        @JsonProperty("fcstValue")
        public String fcstValue; // Category에 따른 예보값
    }
}
