package fan.akua.testapp;

import java.util.Random;

public class Shell {
    private volatile boolean isRunning;
    private long[] mockData;

    public Shell() {
        mockData = new long[0xdeed];
    }

    public String run() {
        isRunning = true;
        try {
            Thread.sleep(Math.abs(new Random().nextInt() % 5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isRunning = false;
        return "" + new Random().nextInt() % 100;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void destroy() {
        mockData = null;
        System.gc();
    }
}
