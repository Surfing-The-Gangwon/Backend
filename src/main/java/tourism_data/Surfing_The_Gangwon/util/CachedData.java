package tourism_data.Surfing_The_Gangwon.util;

public class CachedData<T> {
    private final T data;
    private final long timestamp;

    public CachedData(T data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    // 데이터 만료 여부
    public boolean isExpired(int ttlMinutes) {
        long now = System.currentTimeMillis();
        long expiredTime = timestamp + (ttlMinutes * 60 * 1000L);
        return now > expiredTime;
    }
}
