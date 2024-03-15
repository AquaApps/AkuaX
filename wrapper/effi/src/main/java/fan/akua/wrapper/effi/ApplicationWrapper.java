package fan.akua.wrapper.effi;

import android.app.Application;

public class ApplicationWrapper {
    private volatile static Application mApplication;

    public static Application get() {
        return mApplication;
    }

    public static void set(Application application) {
        mApplication = application;
    }
}
