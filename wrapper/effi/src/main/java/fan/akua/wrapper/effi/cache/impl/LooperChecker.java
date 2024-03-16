package fan.akua.wrapper.effi.cache.impl;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fan.akua.wrapper.effi.cache.Checkable;
import fan.akua.wrapper.effi.cache.Checker;

// todo: memory leak, messageQueue not call removeIdleHandler
public class LooperChecker implements Checker, MessageQueue.IdleHandler {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ArrayList<Checkable> checkables = new ArrayList<>();

    public LooperChecker() {
        this(Looper.myQueue());
    }

    public LooperChecker(MessageQueue messageQueue) {
        messageQueue.addIdleHandler(this);
    }

    @Override
    public void addCheckable(Checkable checkable) {
        lock.writeLock().lock();
        checkables.add(checkable);
        lock.writeLock().unlock();
    }

    @Override
    public void removeCheckable(Checkable checkable) {
        lock.writeLock().lock();
        checkables.remove(checkable);
        lock.writeLock().unlock();
    }

    @Override
    public boolean queueIdle() {
        lock.readLock().lock();
        ArrayList<Checkable> tmpList = new ArrayList<>(checkables);
        lock.readLock().unlock();
        for (Checkable checkable : tmpList) {
            checkable.checkOnce();
        }
        return true;
    }
}
