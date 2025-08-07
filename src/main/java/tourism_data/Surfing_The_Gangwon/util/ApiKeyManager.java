package tourism_data.Surfing_The_Gangwon.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiKeyManager {
    public enum ApiKeyType {
        // 기상청 날씨누리
        WEATHER_API("WEATHER_API_KEY"),
        // 기상청 API 허브
        HUB_API("API_HUB_AUTH_KEY");

        private final String keyName;

        ApiKeyType(String keyName) {
            this.keyName = keyName;
        }

        public String getKeyName() {
            return keyName;
        }
    }

    public static String getApiKey(ApiKeyType keyType) {
        String keyName = keyType.getKeyName();

        // 환경변수에서 API 키 가져오기
        String apiKey = System.getenv(keyName);
        if (isValid(apiKey)) {
            return apiKey;
        }

        // VM Options에서 API 키 가져오기
        apiKey = System.getProperty(keyName);
        if (isValid(apiKey)) {
            return apiKey;
        }

        // .env에서 API 키 가져오기
        apiKey = getApiKeyFromEnvFile(keyName);
        if (isValid(apiKey)) {
            return apiKey;
        }

        log.warn("API Key not found : {}", keyName);
        return  null;
    }

    private static boolean isValid(String key) {
        return key != null && !key.trim().isEmpty();
    }

    private static String getApiKeyFromEnvFile(String keyName) {
        try {
            Path envPath = Paths.get(".env");
            if (Files.exists(envPath)) {
                List<String> lines = Files.readAllLines(envPath);
                String prefix = keyName + "=";

                for (String line : lines) {
                    if (line.startsWith(prefix)) {
                        return line.substring(prefix.length()).trim();
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Failed to read .env file: {}", e.getMessage());
        }

        return null;
    }
}
