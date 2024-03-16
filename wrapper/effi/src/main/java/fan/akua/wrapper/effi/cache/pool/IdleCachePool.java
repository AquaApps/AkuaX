package fan.akua.wrapper.effi.cache.pool;

import android.os.Looper;
import android.os.MessageQueue;

import java.util.concurrent.CopyOnWriteArrayList;

import fan.akua.wrapper.effi.cache.Checker;
import fan.akua.wrapper.effi.cache.Instance;
import fan.akua.wrapper.effi.cache.impl.IdleCache;
import fan.akua.wrapper.effi.cache.impl.LooperChecker;

public class IdleCachePool<T> {
    private final Instance<T> instanceMaker;
    private final CopyOnWriteArrayList<IdleCache<T>> caches;
    private final Checker checker;


    public IdleCachePool(Instance<T> instanceMaker) {
        this(instanceMaker, Looper.myQueue());
    }

    public IdleCachePool(Instance<T> instanceMaker, MessageQueue messageQueue) {
        this.instanceMaker = instanceMaker;
        this.caches = new CopyOnWriteArrayList<>();
        this.checker = new LooperChecker(messageQueue);
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
