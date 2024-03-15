package fan.akuax.widget.fixes;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Window;
import android.widget.MediaController;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class FixKeyEventMediaController extends MediaController {
    public FixKeyEventMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixKeyEventMediaController(Activity activity) {
        this(activity, true);
    }

    public FixKeyEventMediaController(Activity activity, boolean useFastForward) {
        super(activity, useFastForward);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Field mWindow = (Field) HiddenApiBypass.getInstanceFields(MediaController.class).parallelStream().filter((Predicate<Field>) o1 -> o1.getName().equals("mWindow")).findFirst().get();
            mWindow.setAccessible(true);
            try {
                Window window = (Window) mWindow.get(FixKeyEventMediaController.this);
                assert window != null;
                window.setCallback(activity.getWindow().getCallback());
                Log.d("simon", "try to hook " + mWindow.getName());
            } catch (IllegalAccessException e) {
                Log.d("simon", "failed to hook " + mWindow.getName() + " :" + e.getMessage());
            }
        }
    }
}
