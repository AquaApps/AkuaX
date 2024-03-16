package fan.akua.wrapper.effi.cache;

public interface Instance<T> {
    long DEFAULT_MIN_TIME_BETWEEN_CHECKS = 60 * 1000;
    long DEFAULT_MIN_COUNT_BETWEEN_CHECKS = 3;

    default long checkInterval() {
        return DEFAULT_MIN_TIME_BETWEEN_CHECKS;
    }

    default long checkCount() {
        return DEFAULT_MIN_COUNT_BETWEEN_CHECKS;
    }

    T makeInstance();

    boolean isUsing(T instance);

    void destroyInstance(T instance);
}
