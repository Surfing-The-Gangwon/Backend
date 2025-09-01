package tourism_data.Surfing_The_Gangwon.mapper;

import java.util.Arrays;
import java.util.Set;

/**
 * (기간별 단기 해상 예보 조회 시 사용)
 * Seashore 엔티티의 beachCode 필드에 따른 해양 기상 에보구역 코드 매핑
 * 그러나 해변이 계속 늘어날 수록 수동적으로 값을 추가해줄 수 없기에 따로 DB 테이블로 분리 필요
 *
 * 강원 북부 : 12C20103
 * 강원 중부 : 12C20102
 * 강원 남부: 12C20101 (추후 추가 예정)
 */
public enum BeachRegIdMapper {
    GOSEONG("12C20103", Set.of("231", "236", "243", "245")),
    SOKCHO("12C20103", Set.of("201", "202")),
    GANGNEUNG("12C20102", Set.of("176", "179", "172", "174")),
    YANGYANG("12C20103", Set.of("249", "250", "262", "263"));

    // 예보구역 코드
    private final String regId;
    // 해당 관측지점 지역에 해당하는 해변 코드들 집합
    private final Set<String> beachCodes;

    BeachRegIdMapper(String regId, Set<String> beachCodes) {
        this.regId = regId;
        this.beachCodes = beachCodes;
    }

    public static String getRegId(String beachCode) {
        return Arrays.stream(values())
            .filter(mapper -> mapper.beachCodes.contains(beachCode))
            .findFirst()
            .map(beach -> beach.regId)
            .orElse(null);
    }
}
