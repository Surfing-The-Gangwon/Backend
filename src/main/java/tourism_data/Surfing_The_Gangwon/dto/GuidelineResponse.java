package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Guideline;

@Builder
public record GuidelineResponse(
    String title,
    String contents,
    String imgUrl
) {

    public static GuidelineResponse create(Guideline guideline) {
        return GuidelineResponse.builder()
            .title(guideline.getTitle())
            .contents(guideline.getContents())
            .imgUrl(guideline.getImageUrl())
            .build();
    }
}
