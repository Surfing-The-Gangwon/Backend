package tourism_data.Surfing_The_Gangwon.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import java.time.Duration;
import tourism_data.Surfing_The_Gangwon.Constants.URL.WEATHER;
import tourism_data.Surfing_The_Gangwon.dto.request.BaseRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.BeachForecastRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.DailyForecastRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.WaterTempRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.WavePeriodRequest;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.WaterTempResponse;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class WeatherClient {

    private final WebClient webClient;

    public WeatherClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getDailyRangeForecast(DailyForecastRequest request) {
        String fullUrl = String.format("%sreg=%s&tmfc1=%s&tmfc2=%s&disp=%s&help=%s&authKey=%s",
            WEATHER.SHORT_RANGE_FORECAST,
            request.reg(),
            request.tmfc1(),
            request.tmfc2(),
            request.disp(),
            request.help(),
            request.authKey());

        return webClient.get()
            .uri(fullUrl)
            .retrieve()
            .bodyToMono(String.class)
            .timeout(Duration.ofSeconds(30))
            .block();
    }

    public String getWavePeriod(WavePeriodRequest request) {
        String fullUrl = String.format("%stm=%s&stn=%d&help=%d&authKey=%s",
            WEATHER.WAVE_PERIOD,
            request.tm(),
            request.stn(),
            request.help(),
            request.authKey());

        return webClient.get()
            .uri(fullUrl)
            .retrieve()
            .bodyToMono(String.class)
            .timeout(Duration.ofSeconds(30))
            .block();
    }

    public WaterTempResponse getWeaterTemp(WaterTempRequest request) {
        // 먼저 String으로 응답을 받아서 로깅
//        String rawResponse = webClient.get()
//                .uri(uriBuilder -> {
//                    URI uri = uriBuilder
//                            .path(WEATHER.WATER_TEMP)
//                            .queryParam(BaseRequest.SERVICE_KEY, UriUtils.decode(request.getServiceKey(), StandardCharsets.UTF_8))
//                            .queryParam(BaseRequest.PAGE_NO, request.getPageNo())
//                            .queryParam(BaseRequest.NUM_OF_ROWS, request.getNumOfRows())
//                            .queryParam(BaseRequest.DATA_TYPE, request.getDataType())
//                            .queryParam(BaseRequest.BEACH_NUM, request.getBeachNum())
//                            .queryParam(WaterTempRequest.SEARCH_TIME, request.getSearchTime())
//                            .build();
//                    log.info("API Request URL: {}", uri);
//                    return uri;
//                })
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        log.info("Raw API Response: {}", rawResponse);

        // 이제 WaterTempResponse로 파싱
        WaterTempResponse response = webClient.get()
                .uri(uriBuilder -> {
                    return uriBuilder
                            .path(WEATHER.WATER_TEMP)
                            .queryParam(BaseRequest.SERVICE_KEY, UriUtils.decode(BaseRequest.getServiceKey(), StandardCharsets.UTF_8))
                            .queryParam(BaseRequest.PAGE_NO, request.getPageNo())
                            .queryParam(BaseRequest.NUM_OF_ROWS, request.getNumOfRows())
                            .queryParam(BaseRequest.DATA_TYPE, request.getDataType())
                            .queryParam(BaseRequest.BEACH_NUM, request.getBeachNum())
                            .queryParam(WaterTempRequest.SEARCH_TIME, request.getSearchTime())
                            .build();
                })
                .retrieve()
                .bodyToMono(WaterTempResponse.class)
                .timeout(Duration.ofSeconds(30))
                .block();

        if (response == null) {
            throw new IllegalStateException("Empty response from Weather API");
        }

        return response;
    }

    public BeachForecastResponse getBeachForecast(BeachForecastRequest request) {
        BeachForecastResponse response = webClient.get()
            .uri(uriBuilder -> {
                return uriBuilder
                    .path(WEATHER.BEACH_FORECAST)
                    .queryParam(BaseRequest.SERVICE_KEY, UriUtils.decode(request.getServiceKey(), StandardCharsets.UTF_8))
                    .queryParam(BaseRequest.PAGE_NO, request.getPageNo())
                    .queryParam(BaseRequest.NUM_OF_ROWS, request.getNumOfRows())
                    .queryParam(BaseRequest.DATA_TYPE, request.getDataType())
                    .queryParam(BaseRequest.BEACH_NUM, request.getBeachNum())
                    .queryParam(BeachForecastRequest.BASE_DATE, request.getBaseDate())
                    .queryParam(BeachForecastRequest.BASE_TIME, request.getBaseTime())
                    .build();
            })
            .retrieve()
            .bodyToMono(BeachForecastResponse.class)
            .timeout(Duration.ofSeconds(30))
            .block();

        if (response == null) {
            throw new IllegalStateException("Empty response from Weather API");
        }

        return response;
    }
}
