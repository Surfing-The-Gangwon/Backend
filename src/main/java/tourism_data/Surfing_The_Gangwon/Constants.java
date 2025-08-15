package tourism_data.Surfing_The_Gangwon;

public class Constants {
    private Constants() {}

    public static class URL {

        public static class WEATHER {
            public static final String BASE_URL = "https://apis.data.go.kr";
            public static final String WATER_TEMP = "/1360000/BeachInfoservice/getTwBuoyBeach";
            public static final String BEACH_FORECAST = "/1360000/BeachInfoservice/getVilageFcstBeach";
            public static final String WAVE_PERIOD = "https://apihub.kma.go.kr/api/typ01/url/kma_buoy.php?";
            public static final String SHORT_RANGE_FORECAST = "https://apihub.kma.go.kr/api/typ01/url/fct_afs_do.php?";
            public static final String UV_FORECAST = "/1360000/LivingWthrIdxServiceV4/getUVIdxV4";
        }
    }

    public static class Format {
        public static final String DATE_FORMAT_ONE_LINE = "yyyyMMddHHmm";
    }

    public static class Time {
        // 기상청 단기예보 호출시 호출가능한 시간대
        public static final int[] validTimes = new int[]{2, 5, 8, 11, 14, 17, 20, 23};
    }

    // 기상청 API 예보 카테고리 코드
    public static class ForecastCategory {
        public static final String TMP = "TMP"; // 기온
        public static final String WAV = "WAV"; // 파고
        public static final String VEC = "VEC"; // 풍향
        public static final String WSD = "WSD"; // 풍속
    }

    public static class Unit {
        public static final String CELSIUS = "°C";
        public static final String MS = "m/s";
        public static final String SECONDS = "S";
        public static final String METER = "M";
    }

    public static class MarkerType {
        public static final String BEACH = "해변";
        public static final String SURFING_SHOP = "서핑샵";
        public static final String SURFING_SCHOOL = "서핑스쿨";
    }
}
