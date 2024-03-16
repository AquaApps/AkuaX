package fan.akua.testapp;

import java.util.Random;

public class Shell {
    private volatile boolean isRunning;

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

    }
}
