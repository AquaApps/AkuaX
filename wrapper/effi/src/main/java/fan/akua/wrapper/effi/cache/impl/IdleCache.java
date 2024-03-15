package fan.akua.wrapper.effi.cache.impl;

import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicInteger;

import fan.akua.wrapper.effi.cache.Cache;
import fan.akua.wrapper.effi.cache.Checkable;
import fan.akua.wrapper.effi.cache.Checker;
import fan.akua.wrapper.effi.cache.Instance;

public class IdleCache<T> implements Cache<T>, Checkable {
    private final Instance<T> instanceWrapper;
    private volatile long lastCheckTime;
    private volatile T instance;
    private final AtomicInteger count = new AtomicInteger(1);
    private final Checker checker;

    public IdleCache(Instance<T> instanceMaker) {
        this(instanceMaker, new LooperChecker());
    }

    public IdleCache(Instance<T> instanceMaker, Checker checker) {
        this.instanceWrapper = instanceMaker;
        this.lastCheckTime = SystemClock.uptimeMillis();
        this.checker = checker;
    }

    @Override
    public T get() {
        count.incrementAndGet();
        if (instance == null) {
            instance = instanceWrapper.makeInstance();
        }
        checker.addCheckable(this);
        return instance;
    }

    @Override
    public boolean check() {
        final boolean clear = count.get() < 3;
        if (clear) {
            if (!instanceWrapper.isUsing(instance))
                destroy();
        }
        count.set(0);
        return clear;
    }

    @Override
    public void destroy() {
        instance = null;
        checker.removeCheckable(this);
    }

    @Override
    public boolean isUsing() {
        if (instance == null) {
            return false;
        } else {
            return instanceWrapper.isUsing(instance);
        }
    }

    @Override
    public boolean checkOnce() {
        final long now = SystemClock.uptimeMillis();
        if ((lastCheckTime + instanceWrapper.checkInterval()) < now) {
            return check();
        }
        lastCheckTime = now;
        return false;
    }
}
