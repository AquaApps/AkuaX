package fan.akua.wrapper.effi;

import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicInteger;

public class EffiCacheWrapper<T> {
    private static final long MIN_TIME_BETWEEN_CHECKS = 60 * 1000; // 单位毫秒
    private volatile T instance;
    private volatile long lastCheckTime;
    private final AtomicInteger count = new AtomicInteger(1);
    private final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            final long now = SystemClock.uptimeMillis();
            if ((lastCheckTime + MIN_TIME_BETWEEN_CHECKS) < now) {
                return check();
            }
            lastCheckTime = now;
            // 返回 false 表示执行后就将该回调移除掉，返回 true 表示该 IdleHandler 一直处于活跃状态
            return false;
        }
    };

    public EffiCacheWrapper(T instance) {
        this.instance = instance;
        this.lastCheckTime = SystemClock.uptimeMillis();
        Looper.myQueue().addIdleHandler(idleHandler);
    }

    public T get() {
        count.incrementAndGet();
        return instance;
    }

    protected boolean check() {
        final boolean clear = count.get() < 3;
        if (clear) {
            destroyInstance();
        }
        count.set(0);
        return clear;
    }

    public void destroyInstance() {
        instance = null;
        Looper.myQueue().removeIdleHandler(idleHandler);
    }

    @Override
    protected void finalize() throws Throwable {
        destroyInstance();
        super.finalize();
    }
}
