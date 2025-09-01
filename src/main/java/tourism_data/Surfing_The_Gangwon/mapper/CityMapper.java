package tourism_data.Surfing_The_Gangwon.mapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * (자외선 지수 조회 시 사용)
 * Seashore 엔티티의 beachCode 필드에 따른 지역별 행정구역 코드 매핑
 * 그러나 해변이 계속 늘어날 수록 수동적으로 값을 추가해줄 수 없기에 따로 DB 테이블로 분리 필요
 *
 */
public enum CityMapper {
    GOSEONG("5182000000", Set.of("231", "236", "243", "245")),
    SOKCHO("5121000000", Set.of("201", "202")),
    GANGNEUNG("5115000000", Set.of("176", "179", "172", "174")),
    YANGYANG("5183000000", Set.of("249", "250", "262", "263"));

    // 행졍구역 코드
    private final String areaNo;
    // 해당 행정구역에 해당하는 해변 코드들 집합
    private final Set<String> beachCodes;

    CityMapper(String areaNo, Set<String> beachCodes) {
        this.areaNo = areaNo;
        this.beachCodes = beachCodes;
    }
    public static String getAreaNo(String beachCode) {
        return Objects.requireNonNull(Arrays.stream(values())
            .filter(mapper -> mapper.beachCodes.contains(beachCode))
            .findFirst()
            .map(beach -> beach.areaNo)
            .orElse(null));
    }
}
