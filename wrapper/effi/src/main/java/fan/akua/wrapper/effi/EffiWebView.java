package fan.akua.wrapper.effi;

import android.app.Application;
import android.os.Looper;
import android.webkit.WebView;

public class EffiWebView {
    private static boolean webViewLoaded;

    public void effi(Application application) {
        Looper.myQueue().addIdleHandler(() -> {
            if (webViewLoaded) return false;
            new WebView(application);
            webViewLoaded = true;
            return false;
        });
    }
}
