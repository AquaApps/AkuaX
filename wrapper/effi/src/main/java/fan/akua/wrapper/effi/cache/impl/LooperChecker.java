package fan.akua.wrapper.effi.cache.impl;

import android.os.Looper;
import android.os.MessageQueue;

import java.util.concurrent.CopyOnWriteArrayList;

import fan.akua.wrapper.effi.cache.Checkable;
import fan.akua.wrapper.effi.cache.Checker;

// todo: memory leak, messageQueue not call removeIdleHandler
public class LooperChecker implements Checker, MessageQueue.IdleHandler {
    private final CopyOnWriteArrayList<Checkable> checkables = new CopyOnWriteArrayList<>();

    public LooperChecker() {
        this(Looper.myQueue());
    }

    public LooperChecker(MessageQueue messageQueue) {
        messageQueue.addIdleHandler(this);
    }

    @Override
    public void addCheckable(Checkable checkable) {
        checkables.add(checkable);
    }

    @Override
    public void removeCheckable(Checkable checkable) {
        checkables.remove(checkable);
    }

    @Override
    public boolean queueIdle() {
        for (Checkable checkable : checkables) {
            checkable.checkOnce();
        }
        return false;
    }
}
