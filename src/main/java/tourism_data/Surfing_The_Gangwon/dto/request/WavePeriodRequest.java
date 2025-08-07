package tourism_data.Surfing_The_Gangwon.dto.request;

import lombok.Builder;

@Builder
public record WavePeriodRequest(
    String tm, // 년월일시분(KST)
    Integer stn, // 해당 지점의 정보 표출 (0 이거나 없으면 전체지점)
    Integer help, // 1 이면 필드에 대한 약간의 도움말 추가 (0 이거나 없으면 없음)
    String authKey // 발급된 API 인증키
) {
}
