package tourism_data.Surfing_The_Gangwon.mapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * (파도 주기 요청 시 사용)
 * Seashore 엔티티의 beachCode 필드에 따른 해양 기상 관측지점 코드 매핑
 * 그러나 해변이 계속 늘어날 수록 수동적으로 값을 추가해줄 수 없기에 따로 DB 테이블로 분리 필요
 */
public enum BeachStationMapper {
    SOKCHO("22310", Set.of()),
    GANGNEUNG("22311", Set.of("176", "179", "172", "174")),
    YANGYANG("22305", Set.of("249", "250", "262", "263"));

    // 관측지점 코드
    private final String stationCode;
    // 해당 관측지점 지역에 해당하는 해변 코드들 집합
    private final Set<String> beachCodes;

    BeachStationMapper(String stationCode, Set<String> beachCodes) {
        this.stationCode = stationCode;
        this.beachCodes = beachCodes;
    }

    public static Integer getStationCode(String beachCode) {
        return Integer.valueOf(Objects.requireNonNull(Arrays.stream(values())
            .filter(mapper -> mapper.beachCodes.contains(beachCode))
            .findFirst()
            .map(beach -> beach.stationCode)
            .orElse(null)));
    }
}
