package tourism_data.Surfing_The_Gangwon.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.SeaTempResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlexibleItemsDeserializer extends JsonDeserializer<List<SeaTempResponse.Item>> {
    
    @Override
    public List<SeaTempResponse.Item> deserialize(JsonParser p, DeserializationContext ctxt) 
            throws IOException {
        
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        List<SeaTempResponse.Item> items = new ArrayList<>();
        
        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            // 배열인 경우 정상적으로 역직렬화
            while (p.nextToken() != JsonToken.END_ARRAY) {
                SeaTempResponse.Item item = mapper.readValue(p, SeaTempResponse.Item.class);
                items.add(item);
            }
        } else if (p.getCurrentToken() == JsonToken.START_OBJECT) {
            // 단일 객체인 경우 리스트에 하나의 아이템으로 추가
            SeaTempResponse.Item item = mapper.readValue(p, SeaTempResponse.Item.class);
            items.add(item);
        } else if (p.getCurrentToken() == JsonToken.VALUE_STRING && p.getText().isEmpty()) {
            // 빈 문자열인 경우 빈 리스트 반환
            return items;
        }
        
        return items;
    }
}