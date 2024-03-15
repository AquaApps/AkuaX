package fan.akua.wrapper.effi.cache.pool;

import java.util.concurrent.CopyOnWriteArrayList;

import fan.akua.wrapper.effi.cache.Checker;
import fan.akua.wrapper.effi.cache.Instance;
import fan.akua.wrapper.effi.cache.impl.IdleCache;
import fan.akua.wrapper.effi.cache.impl.LooperChecker;

public class IdleCachePool<T> {
    private final Instance<T> instanceMaker;
    private final CopyOnWriteArrayList<IdleCache<T>> caches;
    private final Checker checker = new LooperChecker();

    public IdleCachePool(Instance<T> instanceMaker) {
        this.instanceMaker = instanceMaker;
        this.caches = new CopyOnWriteArrayList<>();
    }

    public T get() {
        for (IdleCache<T> instance : caches) {
            if (!instance.isUsing()) {
                return instance.get();
            }
        }
        IdleCache<T> tIdleCache = new IdleCache<>(instanceMaker, checker);
        caches.add(tIdleCache);
        return tIdleCache.get();
    }

}
