package fan.akua.widget.borderview.internal;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * To extend the lifecycle of raw view, make them implement BorderView.
 */
public interface BorderView {
    interface OnBorderVisibilityChangedListener {
        void onBorderVisibilityChanged(boolean top, boolean oldTop, boolean bottom, boolean oldBottom);
    }

    BorderViewDelegate getBorderViewDelegate();

    void updateBorderStatus();

    default void onDrawBoardTop(Drawable drawable, Canvas canvas) {
        drawable.draw(canvas);
    }

    default void onDrawBoardBottom(Drawable drawable, Canvas canvas) {
        drawable.draw(canvas);
    }

    ////////////// set/get of BorderTopDrawable //////////////
    default Drawable getBorderTopDrawable() {
        return getBorderViewDelegate().getBorderTopDrawable();
    }
    default void setBorderTopDrawable(Drawable drawable) {
        getBorderViewDelegate().setBorderTopDrawable(drawable);
    }

    ////////////// set/get of BorderBottomDrawable //////////////
    default Drawable getBorderBottomDrawable() {
        return getBorderViewDelegate().getBorderBottomDrawable();
    }
    default void setBorderBottomDrawable(Drawable drawable) {
        getBorderViewDelegate().setBorderBottomDrawable(drawable);
    }

    ////////////// set/get of BorderVisibilityChangedListener //////////////
    default OnBorderVisibilityChangedListener getBorderVisibilityChangedListener() {
        return getBorderViewDelegate().getBorderVisibilityChangedListener();
    }
    default void setBorderVisibilityChangedListener(OnBorderVisibilityChangedListener listener) {
        getBorderViewDelegate().setBorderVisibilityChangedListener(listener);
    }

    ////////////// set/get of BorderTopVisibility //////////////
    @BorderEnumWrapper.BorderVisibility
    default int getBorderTopVisibility() {
        return getBorderViewDelegate().getBorderTopVisibility();
    }
    default void setBorderTopVisibility(@BorderEnumWrapper.BorderVisibility int visibility) {
        getBorderViewDelegate().setBorderTopVisibility(visibility);
    }

    ////////////// set/get of BorderBottomVisibility //////////////
    @BorderEnumWrapper.BorderVisibility
    default int getBorderBottomVisibility() {
        return getBorderViewDelegate().getBorderBottomVisibility();
    }
    default void setBorderBottomVisibility(@BorderEnumWrapper.BorderVisibility int visibility){
        getBorderViewDelegate().setBorderBottomVisibility(visibility);
    }

    ////////////// set/get of BorderTopStyle //////////////
    default @BorderEnumWrapper.BorderStyle int getBorderTopStyle(){
        return getBorderViewDelegate().getBorderTopStyle();
    }
    default void setBorderTopStyle(@BorderEnumWrapper.BorderStyle int borderTopStyle){
        getBorderViewDelegate().setBorderTopStyle(borderTopStyle);
    }

    ////////////// set/get of BorderBottomStyle //////////////
    default @BorderEnumWrapper.BorderStyle int getBorderBottomStyle(){
        return getBorderViewDelegate().getBorderBottomStyle();
    }
    default void setBorderBottomStyle(@BorderEnumWrapper.BorderStyle int borderBottomStyle){
        getBorderViewDelegate().setBorderBottomStyle(borderBottomStyle);
    }

    default boolean isShowingTopBorder() {
        return getBorderViewDelegate().isShowingTopBorder();
    }

    default boolean isShowingBottomBorder() {
        return getBorderViewDelegate().isShowingBottomBorder();
    }
}
