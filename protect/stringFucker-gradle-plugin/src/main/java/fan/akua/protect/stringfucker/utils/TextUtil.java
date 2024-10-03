package fan.akua.protect.stringfucker.utils;

public final class TextUtil {
    public static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            return s.isEmpty();
        }
    }
}
