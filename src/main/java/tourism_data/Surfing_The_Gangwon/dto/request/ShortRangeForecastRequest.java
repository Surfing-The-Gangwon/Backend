package tourism_data.Surfing_The_Gangwon.dto.request;

import lombok.Builder;

@Builder
public record ShortRangeForecastRequest(
    String reg, // 예보구역코드
    String tmfc1, // 조회 기간: [tmfc1 ~ tmfc2] : 년월일시(KST)
    String tmfc2, // 조회 기간: [tmfc1 ~ tmfc2] : 년월일시(KST)
    String disp, // 0 : 변수별로 일정한 길이 유지, 1 : 구분자(,)로 구분
    String help, // 1(도움말 정보 표시)
    String authKey // API 인증키
) {
}
