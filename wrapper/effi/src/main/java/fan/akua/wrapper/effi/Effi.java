package fan.akua.wrapper.effi;


import android.app.Application;
import android.os.Looper;
import android.webkit.WebView;

public final class Effi {
    public static void preInitWebView() {
        Application application = ApplicationWrapper.get();
        EffiWebView.preInit(application);
    }
}
