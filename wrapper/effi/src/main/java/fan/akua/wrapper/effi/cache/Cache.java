package fan.akua.wrapper.effi.cache;

public interface Cache<T> {
    T get();

    boolean check();

    void destroy();

    boolean isUsing();
}
