package fan.akua.testapp;

import android.util.Log;

import java.util.HashMap;

public class Trace {
    private static final String TAG = "simon trace";
    private static final HashMap<String, Long> traceMap = new HashMap<>();

    public static void start(String tag) {
        traceMap.put(tag, System.nanoTime());
    }

    public static void end(String tag) {
        Long l = traceMap.get(tag);
        if (l == null) {
            Log.e(TAG, "tag of " + tag + "  not call start");
            return;
        }
        Log.e(TAG, "tag of " + tag + "  spend " + (System.nanoTime() - l));
    }
}
