package fan.akua.fucker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;

import androidx.annotation.Nullable;

import java.lang.reflect.Proxy;

public class Fucker extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return  (IBinder) Proxy.newProxyInstance(Zygote.class.getClassLoader(), IBinder.class.getInterfaces(), (proxy, method, args) -> {
            if (method.getName().equals("onTransact") && args[0].equals(45136)) {
                Parcel reply = (Parcel) args[2];
                reply.writeInt(new Zygote(){{
                        System.loadLibrary("zygote");
                    }}.getFucked());
            }
            return method.invoke(proxy, args);
        });
    }
}
