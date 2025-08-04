package tourism_data.Surfing_The_Gangwon.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tourism_data.Surfing_The_Gangwon.Constants.URL.WEATHER;
import tourism_data.Surfing_The_Gangwon.dto.request.BaseRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.SeaTempRequest;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.SeaTempResponse;

import java.net.URI;

@Slf4j
@Component
public class WeatherClient {

    private final WebClient webClient;

    public WeatherClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public SeaTempResponse getSeaTemp(SeaTempRequest request) {
        SeaTempResponse response = webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path(WEATHER.SEA_TEMP)
                            .queryParam(BaseRequest.SERVICE_KEY, request.getServiceKey())
                            .queryParam(BaseRequest.PAGE_NO, request.getPageNo())
                            .queryParam(BaseRequest.NUM_OF_ROWS, request.getNumOfRows())
                            .queryParam(BaseRequest.DATA_TYPE, request.getDataType())
                            .queryParam(BaseRequest.BEACH_NUM, request.getBeachNum())
                            .queryParam(SeaTempRequest.SEARCH_TIME, request.getSearchTime())
                            .build();
                    log.info("API Request URL: {}", uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(SeaTempResponse.class)
                .block();

        if (response == null) {
            throw new IllegalStateException("Empty response from Weather API");
        }

        return response;
    }
}
