package tourism_data.Surfing_The_Gangwon.util;

import lombok.Builder;

@Builder
public class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static Range of(int start, int end) {
        return Range.builder()
            .start(start)
            .end(end)
            .build();
    }

    public boolean contains(int value) {
        return value >= start && value <= end;
    }
}
