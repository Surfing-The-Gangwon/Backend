package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Lesson;

@Builder
public record LessonDto(
    String title,
    String contents,
    Integer classTime,
    Integer originalPrice,
    Integer discountedPrice
) {
    public static LessonDto create(Lesson lesson) {
        return LessonDto.builder()
            .title(lesson.getTitle())
            .contents(lesson.getContents())
            .classTime(lesson.getClassTime())
            .originalPrice(lesson.getOriginalPrice())
            .discountedPrice(lesson.getDiscountedPrice())
            .build();
    }
}
