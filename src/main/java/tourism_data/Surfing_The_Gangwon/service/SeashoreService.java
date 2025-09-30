package tourism_data.Surfing_The_Gangwon.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import tourism_data.Surfing_The_Gangwon.common.Constants.Format;
import tourism_data.Surfing_The_Gangwon.common.Constants.MarkerType;
import tourism_data.Surfing_The_Gangwon.common.Constants.Time;
import tourism_data.Surfing_The_Gangwon.common.Constants.Unit;
import tourism_data.Surfing_The_Gangwon.config.ThreadPoolConfig;
import tourism_data.Surfing_The_Gangwon.dto.BeachForecast;
import tourism_data.Surfing_The_Gangwon.dto.CityDto;
import tourism_data.Surfing_The_Gangwon.dto.MarkerInfo;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDetailResponse;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDto;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.request.BeachForecastRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.DailyForecastRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.DailyTideRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.UVRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.WaterTempRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.WavePeriodRequest;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyForecastResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyTideFilteredResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyTideFilteredResponse.DailyTideDto;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.UVResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.WaterTempResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.WavePeriodResponse;
import tourism_data.Surfing_The_Gangwon.entity.Marker;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;
import tourism_data.Surfing_The_Gangwon.integration.WeatherClient;
import tourism_data.Surfing_The_Gangwon.mapper.BeachRegIdMapper;
import tourism_data.Surfing_The_Gangwon.mapper.BeachStationMapper;
import tourism_data.Surfing_The_Gangwon.mapper.CityMapper;
import tourism_data.Surfing_The_Gangwon.repository.CityRepository;
import tourism_data.Surfing_The_Gangwon.repository.MarkerRepository;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;
import tourism_data.Surfing_The_Gangwon.util.ApiKeyManager;
import tourism_data.Surfing_The_Gangwon.util.ApiKeyManager.ApiKeyType;
import tourism_data.Surfing_The_Gangwon.util.CachedData;
import tourism_data.Surfing_The_Gangwon.util.DailyForecastParser;

@Slf4j
@Service
public class SeashoreService {
    private final SeashoreRepository seashoreRepository;
    private final MarkerRepository markerRepository;
    private final CityRepository cityRepository;
    private final WeatherClient weatherClient;
    private Executor asyncExecutor;

    // 캐시 저장소 (메모리에 데이터 임시 저장) => 여러 사용자가 동시에 접근 시 안정성을 위해 ConcurrentHashMap 사용
    private final ConcurrentHashMap<String, CachedData<BeachForecastResponse>> forecastCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CachedData<String>> waterTempCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CachedData<String>> wavePeriodCache = new ConcurrentHashMap<>();

    public SeashoreService(SeashoreRepository seashoreRepository, MarkerRepository markerRepository,
        CityRepository cityRepository,
        WeatherClient weatherClient) {
        this.seashoreRepository = seashoreRepository;
        this.markerRepository = markerRepository;
        this.cityRepository = cityRepository;
        this.weatherClient = weatherClient;
    }

    @PostConstruct
    public void init() {
        // cpu 코어 수 조회 (jvm이 사용하 수 있는 cpu 코어 수 반환) => 물리 코어 + 하이퍼스레딩
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        // 스레드풀 크기 계산 (CPU 코어 수 * 배수) => CPU가 api응답을 기다리는 동안 유휴 상태
        // 3배 할당하면 대기 시간 동안 다른 작업 처리 가능
        int calculatedSize = availableProcessors * ThreadPoolConfig.IO_BOUND_MULTIPLIER;

        // 최소값과 최대값 사이 범위 제한 (최소값 <= threadPoolSize <= 최대값)
        int threadPoolSize = Math.max(
            ThreadPoolConfig.MIN_THREAD_POOL_SIZE,
            Math.min(calculatedSize, ThreadPoolConfig.MAX_THREAD_POOL_SIZE)
        );

        // 스레드풀 생성 전 로그 출력
        log.info("스레드풀 초기화: CPU 코어 {}개, 계산된 크기 {}, 최종 크기 {}",
            availableProcessors, calculatedSize, threadPoolSize);
        this.asyncExecutor = Executors.newFixedThreadPool(threadPoolSize);
    }

    @PreDestroy
    public void destroy() {
        if (asyncExecutor instanceof java.util.concurrent.ExecutorService) {
            ((java.util.concurrent.ExecutorService) asyncExecutor).shutdown();
        }
    }

    public List<MarkerInfo> getMarkersBySeashore(Long seashoreId) {
        return markerRepository.findBySeashoreId(seashoreId).stream()
            .map(marker -> MarkerInfo.builder()
                    .id(marker.getMarkerId())
                    .type(marker.getType())
                    .latitude(marker.getLatitude())
                    .longitude(marker.getLongitude())
                    .name(marker.getName())
                    .build()
            )
            .toList();
    }

    public List<SeashoreResponse> getSeashoresByCity(Long cityId) {
        List<Seashore> seashores = seashoreRepository.findByCityId(cityId);
        long startTime = System.currentTimeMillis();

        // 각 해변마다 3개 API를 묶어서 처리
        List<CompletableFuture<SeashoreResponse>> futures = seashores.stream()
            .map(seashore -> {
                CompletableFuture<BeachForecastResponse> forecastFuture =
                    CompletableFuture.supplyAsync(() -> getCachedBeachForecast(seashore.getBeachCode()), asyncExecutor)
                        .orTimeout(10, TimeUnit.SECONDS);
                CompletableFuture<String> waterTempFuture =
                    CompletableFuture.supplyAsync(() -> getCachedWaterTemp(seashore.getBeachCode()), asyncExecutor)
                        .orTimeout(10, TimeUnit.SECONDS);
                CompletableFuture<String> wavePeriodFuture =
                    CompletableFuture.supplyAsync(() -> getCachedWavePeriod(seashore.getBeachCode()), asyncExecutor)
                        .orTimeout(10, TimeUnit.SECONDS);

                return CompletableFuture.allOf(forecastFuture, waterTempFuture, wavePeriodFuture)
                    .thenApply(v -> SeashoreResponse.create(seashore,
                        waterTempFuture.join(),
                        BeachForecast.create(forecastFuture.join()),
                        wavePeriodFuture.join()
                    ))
                    .exceptionally(ex -> {
                        log.error("기상청 API 호출 실패, 해변코드 {}: {}", seashore.getBeachCode(), ex.getMessage());
                        return SeashoreResponse.create(seashore, "", null, "");
                    });
            })
            .toList();

        List<SeashoreResponse> results = futures.stream()
            .map(CompletableFuture::join)
            .toList();

        long endTime = System.currentTimeMillis();
        log.info("전체 기상청 API 호출 소요 시간: {}ms, {} seashores (cityId: {})",
            endTime - startTime, seashores.size(), cityId);

        // 성능 로그 (캐시 적용 효과 확인)
        log.info("캐시 적용 API 호출 완료: {}ms, {} 해변 (도시ID: {})",
            endTime - startTime, seashores.size(), cityId);
        log.info("예상 캐시 적용 효과: API 호출 수 감소, 응답 시간 단축");


        // 일부 API가 빨리 끝나도 다른 API를 기다려야 함.
        // 현재 상황에서는 강원도 해변이 많지 않으므로 각 해변별로 독립적으로 처리하는 방식으로 함
        return results;
    }

    public List<CityDto> getAllCities() {
        return cityRepository.findAll()
            .stream()
            .map(CityDto::create)
            .toList();
    }

    public List<SeashoreDto> getBasicSeashoresByCity(Long cityId) {
        return seashoreRepository.findByCityId(cityId)
            .stream()
            .map(SeashoreDto::create)
            .toList();
    }

    public SeashoreDetailResponse getSeashoreById(Long seashoreId) {
        Seashore seashoreEntity = seashoreRepository.findById(seashoreId)
            .orElseThrow(() -> new RuntimeException("seashore not found"));

        // 특정 해변의 마커만 조회 (가장 정확한 방법)
        List<Marker> markers = markerRepository.findBySeashoreId(seashoreId);

        // BEACH 타입 마커를 필터링
        Marker selectedMarker = markers.stream()
            .filter(m -> MarkerType.BEACH.equals(m.getType()))
            .findFirst()
            .orElse(markers.isEmpty() ? null : markers.getFirst());
        
        Double latitude = selectedMarker != null ? selectedMarker.getLatitude() : null;
        Double longitude = selectedMarker != null ? selectedMarker.getLongitude() : null;

        return SeashoreDetailResponse.create(seashoreEntity, latitude, longitude);
    }

    // 캐시 적용된 수온 조회
    private String getCachedWaterTemp(Integer beachCode) {
        String cacheKey = "waterTemp_" + beachCode + "_" + getCurrentHour();

        CachedData<String> cached = waterTempCache.get(cacheKey);
        // 1시간 TTL
        if (cached != null && !cached.isExpired(60)) {
            log.debug("수온 캐시 : {}", beachCode);
            return cached.getData();
        }

        String freshData = getWaterTemp(beachCode);
        waterTempCache.put(cacheKey, new CachedData<>(freshData, System.currentTimeMillis()));
        return freshData;
    }

    private String getWaterTemp(Integer beachCode) {
        var searchTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        WaterTempRequest request = WaterTempRequest.builder()
            .beachNum(String.valueOf(beachCode))
            .searchTime(searchTime)
            .build();

        WaterTempResponse response = weatherClient.getWeaterTemp(request);
        var items = response.response().body.items.item;
        return items.isEmpty() ? "" : items.getFirst().tw + Unit.CELSIUS;
    }

    private int getCurrentHour() {
        return LocalDateTime.now().getHour();
    }

    private BeachForecastResponse getCachedBeachForecast(Integer beachCode) {
        String cacheKey = "forecast_" + beachCode + "_" + getCurrentHour();

        // 캐시에서 데이터 찾기
        CachedData<BeachForecastResponse> cached = forecastCache.get(cacheKey);

        // 캐시에 데이터가 있고, 만료되지 않았을 경우 (해양 예보의 경우 ttl 30분)
        if (cached != null && !cached.isExpired(30)) {
            log.debug("해양 예보 캐시 : {}", beachCode);
            return cached.getData();
        }

        // 기존 메서드 호출 (최신 데이터 받아옴)
        BeachForecastResponse freshData = getBeachForecast(beachCode);
        // 새로운 데이터 캐시에 저장
        forecastCache.put(cacheKey, new CachedData<>(freshData, System.currentTimeMillis()));
        return freshData;
    }

    private BeachForecastResponse getBeachForecast(Integer beachCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTimes = getValidBaseDateTime(now);
        var dateTime = validTimes.format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        var baseDate = dateTime.substring(0, 8);
        var baseTime = dateTime.substring(8);

        BeachForecastRequest request = BeachForecastRequest.builder()
            .numOfRows(String.valueOf(20))
            .baseDate(baseDate)
            .baseTime(baseTime)
            .beachNum(String.valueOf(beachCode))
            .build();

        return weatherClient.getBeachForecast(request);
    }

    private LocalDateTime getValidBaseDateTime(LocalDateTime currTime) {
        int currHour = currTime.getHour();
        int[] validTimes = Time.validTimes;

        // 현재 시간보다 이전의 가장 가까운 유효 시간 탐색
        for (int i = validTimes.length - 1; i >= 0; i--) {
            if (currHour >= validTimes[i]) {
                return currTime.withHour(validTimes[i]).withMinute(0).withSecond(0).withNano(0);
            }
        }

        // 현재 시간이 02시보다 이전이면, 전날의 23시 사용 (오전 12시 이후, 익일 02시 이전일 경우)
        return currTime.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    // 캐시 적용된 파주기 조회
    private String getCachedWavePeriod(Integer beachCode) {
        String cacheKey = "wavePeriod_" + beachCode + "_" + getCurrentHour();

        CachedData<String> cached = wavePeriodCache.get(cacheKey);
        if (cached != null && !cached.isExpired(15)) {
            log.debug("파주기 캐시 : {}", beachCode);
            return cached.getData();
        }

        String freshData = getWavePeriod(beachCode);
        wavePeriodCache.put(cacheKey, new CachedData<>(freshData, System.currentTimeMillis()));
        return freshData;
    }

    private String getWavePeriod(Integer beachCode) {
        var searchTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        // 지역별 지점번호 변환값 (관측지점 코드 반환)
        var stnCode = BeachStationMapper.getStationCode(String.valueOf(beachCode));

        WavePeriodRequest request = WavePeriodRequest.builder()
            .tm(searchTime)
            .stn(stnCode)
            .help(0)
            .authKey(getApiHubAuthKey())
            .build();

        WavePeriodResponse response = WavePeriodResponse.create(weatherClient.getWavePeriod(request));
        return response.wp() + Unit.SECONDS;
    }

    // 자외선 지수 조회
    public String getUVForecast(Long seashoreId) {
        Seashore seashoreEntity = seashoreRepository.findById(seashoreId)
            .orElseThrow(() -> new RuntimeException("seashore not found"));

        var dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        var areaNo = CityMapper.getAreaNo(String.valueOf(seashoreEntity.getBeachCode()));
        var searchTime = dateTime.substring(0, 10);

        UVRequest request = UVRequest.builder()
            .areaNo(areaNo)
            .time(searchTime)
            .build();

        UVResponse response = weatherClient.getUVForecast(request);
        var items = response.response().body.items.item;
        return items.isEmpty() ? "" : items.getFirst().uvResult;
    }

    // 단기 해상 예보 조회
    public List<DailyForecastResponse> getDailySeaForecast(Long beachCode) {
        var startDateTime = LocalDateTime.now().minusDays(1);
        var startFormatted = startDateTime.format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        var endDateTime = LocalDateTime.now().plusDays(4);
        var endFormatted = endDateTime.format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        var start = startFormatted.substring(0, 10);
        var end = endFormatted.substring(0, 10);
        var regId = BeachRegIdMapper.getRegId(String.valueOf(beachCode));
        DailyForecastRequest request = DailyForecastRequest.builder()
            .reg(regId)
            .tmfc1(start)
            .tmfc2(end)
            .disp("0")
            .help("0")
            .authKey(getApiHubAuthKey())
            .build();

        var response = weatherClient.getDailyRangeForecast(request);
        return DailyForecastParser.parseWeatherData(response);
    }

    // 조석 정보 조회
    public List<DailyTideFilteredResponse> getDailyTideForecast(Long beachCode) {
        var list = new ArrayList<DailyTideFilteredResponse>();
        var startDateTime = LocalDateTime.now().minusDays(1);
        var endDateTime = LocalDateTime.now().plusDays(4);

        while (!startDateTime.isAfter(endDateTime)) {
            var baseDate = startDateTime.format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE)).substring(0, 8);
            DailyTideRequest request = DailyTideRequest.builder()
                .beachNum(String.valueOf(beachCode))
                .baseDate(baseDate)
                .numOfRows(String.valueOf(100))
                .build();
            startDateTime = startDateTime.plusDays(1);

            var response = weatherClient.getDailyTideForecast(request);
            List<DailyTideDto> filtered = response.response().body.items.item
                .stream()
                .filter(item -> !Set.of("FT2", "ET1").contains(item.tiType))
                .map(DailyTideDto::create)
                .toList();

            list.add(DailyTideFilteredResponse.create(filtered));
        }
        return list;
    }

    public static String getApiHubAuthKey() {
        return ApiKeyManager.getApiKey(ApiKeyType.HUB_API);
    }

    // 개발용/디버깅용 (만료된 캐시 정리)
    @Scheduled(cron = "0 0 * * * *") // 매 시 정각마다
    public void cleanExpiredCache() {
        long beforeCount = forecastCache.size() + waterTempCache.size() + wavePeriodCache.size();

        // 만료된 항목들 제거
        forecastCache.entrySet().removeIf(entry -> entry.getValue().isExpired(30));
        waterTempCache.entrySet().removeIf(entry -> entry.getValue().isExpired(60));
        wavePeriodCache.entrySet().removeIf(entry -> entry.getValue().isExpired(15));

        long afterCount = forecastCache.size() + waterTempCache.size() + wavePeriodCache.size();

        if (beforeCount > afterCount) {
            log.info("캐시 정리 완료: {} 개 → {} 개 ({}개 삭제)",
                beforeCount, afterCount, beforeCount - afterCount);
        }
    }
}
