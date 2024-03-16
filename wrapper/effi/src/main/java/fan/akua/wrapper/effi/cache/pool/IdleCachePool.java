package fan.akua.wrapper.effi.cache.pool;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fan.akua.wrapper.effi.cache.Checker;
import fan.akua.wrapper.effi.cache.Instance;
import fan.akua.wrapper.effi.cache.impl.IdleCache;
import fan.akua.wrapper.effi.cache.impl.LooperChecker;

public class IdleCachePool<T> {
    private final Instance<T> instanceMaker;
    private final ArrayList<IdleCache<T>> caches;
    private final Checker checker;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public IdleCachePool(Instance<T> instanceMaker) {
        this(instanceMaker, Looper.myQueue());
    }

    public IdleCachePool(Instance<T> instanceMaker, MessageQueue messageQueue) {
        this.instanceMaker = instanceMaker;
        this.caches = new ArrayList<>();
        this.checker = new LooperChecker(messageQueue);
    }

    public T get() {
        lock.readLock().lock();
        ArrayList<IdleCache<T>> tmpList=new ArrayList<>(caches);
        lock.readLock().unlock();
        for (IdleCache<T> cache : tmpList) {
            if (!cache.isUsing()) {
                return cache.get();
            }
        }

        lock.writeLock().lock();
        IdleCache<T> tIdleCache = new IdleCache<>(instanceMaker, checker);
        caches.add(tIdleCache);
        lock.writeLock().unlock();
        return tIdleCache.get();
    }

}
