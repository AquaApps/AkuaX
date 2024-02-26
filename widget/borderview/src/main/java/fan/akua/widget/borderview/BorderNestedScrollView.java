package fan.akua.widget.borderview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import fan.akua.widget.borderview.internal.BorderEnumWrapper;
import fan.akua.widget.borderview.internal.BorderView;
import fan.akua.widget.borderview.internal.BorderViewDelegate;

public class BorderNestedScrollView extends NestedScrollView implements BorderView {
    private final BorderViewDelegate mBorderViewDelegate;

    public BorderNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public BorderNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.borderViewStyle);
    }

    public BorderNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBorderViewDelegate = new BorderViewDelegate(this, context, attrs, defStyleAttr);
    }

    @Override
    public BorderViewDelegate getBorderViewDelegate() {
        return mBorderViewDelegate;
    }

    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
            int childSize = child.getHeight() + lp.topMargin + lp.bottomMargin;
            int parentSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            scrollRange = Math.max(0, childSize - parentSpace);
        }
        return scrollRange;
    }

    @Override
    public void updateBorderStatus() {
        final int offset = getScrollY();
        final int range = getScrollRange();
        final boolean isShowingTopBorder, isShowingBottomBorder;
        final boolean isTop, isBottom;
        if (range != 0) {
            isTop = offset == 0;
            isBottom = offset == range;
        } else {
            //isTop = isBottom = false;
            return;
        }
        isShowingTopBorder = getBorderTopVisibility() == BorderEnumWrapper.ALWAYS
                || (getBorderTopVisibility() == BorderEnumWrapper.TOP_OR_BOTTOM && isTop)
                || (getBorderTopVisibility() == BorderEnumWrapper.SCROLLED && !isTop);
        isShowingBottomBorder = getBorderBottomVisibility() == BorderEnumWrapper.ALWAYS
                || (getBorderBottomVisibility() == BorderEnumWrapper.TOP_OR_BOTTOM && isBottom)
                || (getBorderBottomVisibility() == BorderEnumWrapper.SCROLLED && !isBottom);

        if (!(isShowingTopBorder() == isShowingTopBorder) || !(isShowingBottomBorder() == isShowingBottomBorder)) {
            getBorderViewDelegate().onBorderVisibilityChanged(
                    isShowingTopBorder,
                    isShowingTopBorder(),
                    isShowingBottomBorder,
                    isShowingBottomBorder()
            );
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        updateBorderStatus();

        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        updateBorderStatus();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        getBorderViewDelegate().onDrawForeground(canvas);
    }
}
