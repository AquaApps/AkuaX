package fan.akua.wrapper.effi.cache;

public interface Instance<T> {
    long DEFAULT_MIN_TIME_BETWEEN_CHECKS = 60 * 1000;

    default long checkInterval() {
        return DEFAULT_MIN_TIME_BETWEEN_CHECKS;
    }

    T makeInstance();

    boolean isUsing(T instance);
}
