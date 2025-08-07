package tourism_data.Surfing_The_Gangwon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyForecastResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DailyForecastParser {

    private final ObjectMapper objectMapper;

    public DailyForecastParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static List<DailyForecastResponse> parseWeatherData(String rawData) {
        List<DailyForecastResponse> weatherDataList = new ArrayList<>();

        String cleanedData = cleanData(rawData);
        String[] lines = cleanedData.split("\n");

        for (String line : lines) {
            if (isDataLine(line)) {
                DailyForecastResponse data = parseLine(line);
                if (data != null) {
                    weatherDataList.add(data);
                }
            }
        }

        return weatherDataList;
    }

    private static String cleanData(String rawData) {
        rawData = rawData.replace("#START7777", "")
            .replace("#7777END", "");

        return Arrays.stream(rawData.split("\n"))
            .filter(line -> !line.startsWith("#"))
            .collect(Collectors.joining("\n"));
    }

    private static boolean isDataLine(String line) {
        return line.matches("^\\w+\\s+\\d{12}.*");
    }

    private static DailyForecastResponse parseLine(String line) {
        String[] values = line.trim().split("\\s+");

        if (values.length < 19) {
            return null;
        }

        try {
            String wf = extractQuotedString(line);
            
            return DailyForecastResponse.builder()
                .tmfc(values[1])    // 발표시각
                .tmef(values[2])    // 발효시각
                .sky(values[14])    // 하늘상태
                .w2(values[12])     // 풍향
                .s2(values[13])     // 풍속
                .wh2(values[16])    // 파고
                .wf(wf)             // 예보
                .build();
        } catch (Exception e) {
            return null;
        }
    }

    private static String extractQuotedString(String line) {
        int firstQuote = line.indexOf('"');
        int lastQuote = line.lastIndexOf('"');
        
        if (firstQuote != -1 && lastQuote != -1 && firstQuote != lastQuote) {
            return line.substring(firstQuote + 1, lastQuote);
        }
        return "";
    }
}
