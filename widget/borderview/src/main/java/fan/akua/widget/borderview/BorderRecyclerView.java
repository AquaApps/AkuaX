package fan.akua.widget.borderview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import fan.akua.widget.borderview.internal.BorderEnumWrapper;
import fan.akua.widget.borderview.internal.BorderView;
import fan.akua.widget.borderview.internal.BorderViewDelegate;

public class BorderRecyclerView extends RecyclerView implements BorderView {
    private final BorderViewDelegate mBorderViewDelegate;

    public BorderRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BorderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.borderViewStyle);
    }

    public BorderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mBorderViewDelegate = new BorderViewDelegate(this, context, attrs, defStyle);
    }

    @Override
    public BorderViewDelegate getBorderViewDelegate() {
        return mBorderViewDelegate;
    }

    @Override
    public void updateBorderStatus() {
        int offset = computeVerticalScrollOffset();
        int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        final boolean isShowingTopBorder, isShowingBottomBorder;
        final boolean isTop, isBottom;

        isTop = offset == 0;
        isBottom = offset == range;
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
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        getBorderViewDelegate().onDrawForeground(canvas);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        updateBorderStatus();
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
