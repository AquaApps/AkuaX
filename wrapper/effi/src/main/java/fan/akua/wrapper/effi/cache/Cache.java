package fan.akua.wrapper.effi.cache;

public interface Cache<T> {
    T get();

    boolean tryDestroy();

    void destroy();

    boolean isUsing();
}
