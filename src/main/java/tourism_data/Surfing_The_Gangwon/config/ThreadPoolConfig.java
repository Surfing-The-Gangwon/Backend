package tourism_data.Surfing_The_Gangwon.config;

public class ThreadPoolConfig {
    // I/O 바운드 작업의 경우 CPU 코어 수의 배수로 스레드풀 크기 결정(기상청 API는 I/O 대기 시간이 길어서 3배 할당)
    public static final int IO_BOUND_MULTIPLIER = 3;
    // 스레드풀 최소 크기 (CPU 코어가 적은 환경 대비)
    public static final int MIN_THREAD_POOL_SIZE = 10;
    // 스레드풀 최대 크기 (너무 많은 스레드로 인한 오버헤드 방지, 컨텍스트 스위칭 오버헤드)
    public static final int MAX_THREAD_POOL_SIZE = 20;
}
