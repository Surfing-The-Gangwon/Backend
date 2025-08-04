package tourism_data.Surfing_The_Gangwon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseRequest {

    public static final String SERVICE_KEY = "serviceKey";
    public static final String PAGE_NO = "pageNo";
    public static final String NUM_OF_ROWS = "numOfRows";
    public static final String DATA_TYPE = "dataType";
    public static final String BEACH_NUM = "beach_num";

    @Builder.Default
    private String serviceKey = getServiceKeyFromProperties();
    @Builder.Default
    private String numOfRows = "10";
    @Builder.Default
    private String pageNo = "1";
    @Builder.Default
    private String dataType = "JSON";

    @JsonProperty("beach_num")
    private String beachNum;

    private static String getServiceKeyFromProperties() {
        // 환경변수에서 API 키 가져오기
        String apiKey = System.getenv("WEATHER_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }
        
        // VM options에서 API 키 가져오기
        apiKey = System.getProperty("WEATHER_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }
        
        // .env 파일에서 API 키 가져오기
        try {
            java.nio.file.Path envPath = java.nio.file.Paths.get(".env");
            if (java.nio.file.Files.exists(envPath)) {
                java.util.List<String> lines = java.nio.file.Files.readAllLines(envPath);
                for (String line : lines) {
                    if (line.startsWith("WEATHER_API_KEY=")) {
                        return line.substring("WEATHER_API_KEY=".length()).trim();
                    }
                }
            }
        } catch (Exception e) {
            // .env 파일을 읽을 수 없는 경우 무시
        }
        
        return null;
    }
}
