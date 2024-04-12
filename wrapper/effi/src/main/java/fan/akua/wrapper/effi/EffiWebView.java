package fan.akua.wrapper.effi;

import android.app.Application;
import android.os.Looper;
import android.webkit.WebView;

class EffiWebView {
    private static volatile boolean webViewLoaded;

    static void preInit(Application application) {
        if (!webViewLoaded){
            if (Looper.myLooper()==Looper.getMainLooper()) {
                Looper.myQueue().addIdleHandler(() -> {
                    if (webViewLoaded) return false;
                    new WebView(application);
                    webViewLoaded = true;
                    return false;
                });
            }
        }
    }
}
