package tourism_data.Surfing_The_Gangwon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WavePeriodParser {
    public static String parseWeatherData(String rawData) {
        List<String> weatherDataList = new ArrayList<>();

        // 데이터 정제
        String cleanedData = cleanData(rawData);
        String[] lines = cleanedData.split("\n");

        for (String line : lines) {
            if (isDataLine(line)) {
                var data = parseLine(line);
                if (data != null) { weatherDataList.add(data); }
            }
        }

        return weatherDataList.getLast();
    }

    private static String cleanData(String rawData) {
        // START/END 마커 제거
        rawData = rawData.replace("#START7777", "")
            .replace("#7777END", "");

        // 주석 라인 제거
        return Arrays.stream(rawData.split("\n"))
            .filter(line -> !line.startsWith("#"))
            .collect(Collectors.joining("\n"));
    }

    private static boolean isDataLine(String line) {
        // 날짜로 시작하는 라인만 데이터로 처리
        return line.matches("^\\d{12}.*");
    }

    private static String parseLine(String line) {
        // 공백으로 분리 (여러 공백을 하나로)
        String[] values = line.trim().split("\\s+");

        if (values.length < 17) {
            return null; // 필수 필드가 부족한 경우
        }

        try {
            return filterData(values[15]);
        } catch (Exception e) {
            return null;
        }
    }

    private static String filterData(String value) {
        if ("-99".equals(value) || "-99.0".equals(value)) {
            return null; // 결측값 필터 처리
        }
        try {
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
