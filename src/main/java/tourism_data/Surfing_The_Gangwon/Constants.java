package tourism_data.Surfing_The_Gangwon;

public class Constants {
    private Constants() {}

    public static class URL {

        public static class WEATHER {
            public static final String BASE_URL = "https://apis.data.go.kr";
            public static final String SEA_TEMP = "/1360000/BeachInfoservice/getTwBuoyBeach";
        }
    }

    public static class Format {
        public static final String DATE_FORMAT_ONE_LINE = "yyyyMMddHHmm";
    }
}
