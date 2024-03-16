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
        Effi.preInitWebView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}