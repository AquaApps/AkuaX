package fan.akua.fucker;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

public class Zygote extends Instrumentation {
    public static transient volatile int fucked = 0;

    public int getFucked() {
        return fucked;
    }
}
