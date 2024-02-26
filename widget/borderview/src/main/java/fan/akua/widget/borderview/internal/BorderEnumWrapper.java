package fan.akua.widget.borderview.internal;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class BorderEnumWrapper {
    public static final int NEVER = 0;
    public static final int TOP_OR_BOTTOM = 1;
    public static final int SCROLLED = 2;
    public static final int ALWAYS = 3;

    @IntDef({NEVER, TOP_OR_BOTTOM, SCROLLED, ALWAYS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BorderVisibility {
    }

    public static final int INSIDE = 0;
    public static final int OUTSIDE = 1;

    @IntDef({INSIDE, OUTSIDE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BorderStyle {
    }
}
