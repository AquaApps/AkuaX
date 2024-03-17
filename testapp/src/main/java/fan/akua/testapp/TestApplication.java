package fan.akua.testapp;

import android.app.Application;
import android.content.Context;

import fan.akua.wrapper.effi.ApplicationWrapper;
import fan.akua.wrapper.effi.Effi;

public class TestApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ApplicationWrapper.set(this);
        // todo: effi's WebView is not work on Android 8.0
//        Effi.preInitWebView();
        Trace.start("Effi");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
//61071406
//48316146
//52118073

//48859531
//33868698
//36680416