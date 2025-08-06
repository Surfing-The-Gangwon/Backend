package tourism_data.Surfing_The_Gangwon.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlexibleItemsDeserializer extends JsonDeserializer<List<?>> {
    
    @Override
    public List<?> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        List<Object> items = new ArrayList<>();
        
        // 현재 필드의 타입 정보를 가져옴
        JavaType valueType = ctxt.getContextualType();
        if (valueType != null && valueType.hasContentType()) {
            Class<?> itemClass = valueType.getContentType().getRawClass();
            
            if (p.getCurrentToken() == JsonToken.START_ARRAY) {
                // 배열인 경우 정상적으로 역직렬화
                while (p.nextToken() != JsonToken.END_ARRAY) {
                    Object item = mapper.readValue(p, itemClass);
                    items.add(item);
                }
            } else if (p.getCurrentToken() == JsonToken.START_OBJECT) {
                // 단일 객체인 경우 리스트에 하나의 아이템으로 추가
                Object item = mapper.readValue(p, itemClass);
                items.add(item);
            } else if (p.getCurrentToken() == JsonToken.VALUE_STRING && p.getText().isEmpty()) {
                // 빈 문자열인 경우 빈 리스트 반환
                return items;
            }
        }
        
        return items;
    }
}