package fan.akua.widget.borderview.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fan.akua.widget.borderview.R;

/**
 * Real class who complete the rendering of border.
 */
public class BorderViewDelegate {
    private final View mView;
    private final BorderView mBorderView;
    private Drawable borderTopDrawable, borderBottomDrawable;
    @BorderEnumWrapper.BorderVisibility
    private int borderTopVisibility, borderBottomVisibility;
    @BorderEnumWrapper.BorderStyle
    private int borderTopStyle, borderBottomStyle;
    private Boolean isShowingTopBorder, isShowingBottomBorder;
    private BorderView.OnBorderVisibilityChangedListener mBorderVisibilityChangedListener;

    public BorderViewDelegate(View view, Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mView = view;
        mBorderView = (BorderView) view;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BorderView, defStyleAttr, R.style.Widget_BorderView);
        borderTopDrawable = attributes.getDrawable(R.styleable.BorderView_borderTopDrawable);
        borderBottomDrawable = attributes.getDrawable(R.styleable.BorderView_borderBottomDrawable);
        borderTopVisibility = attributes.getInt(R.styleable.BorderView_borderTopVisibility, 0);
        borderBottomVisibility = attributes.getInt(R.styleable.BorderView_borderBottomVisibility, 0);
        borderTopStyle = attributes.getInt(R.styleable.BorderView_borderTopStyle, 0);
        borderBottomStyle = attributes.getInt(R.styleable.BorderView_borderBottomStyle, 0);
        attributes.recycle();
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
                v.addOnLayoutChangeListener(onLayoutChangeListener);
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                v.removeOnLayoutChangeListener(onLayoutChangeListener);
            }
        });
    }

    final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (isShowingTopBorder == null || isShowingBottomBorder == null) {
                isShowingTopBorder = isShowingTopBorder();
                isShowingBottomBorder = isShowingBottomBorder();

                if (getBorderVisibilityChangedListener() != null) {
                    getBorderVisibilityChangedListener().onBorderVisibilityChanged(
                            isShowingTopBorder,
                            borderTopVisibility == BorderEnumWrapper.TOP_OR_BOTTOM || borderTopVisibility == BorderEnumWrapper.ALWAYS,
                            isShowingBottomBorder,
                            borderBottomVisibility == BorderEnumWrapper.ALWAYS);
                }
            }
            v.removeOnLayoutChangeListener(this);
        }
    };

    public void onDrawForeground(Canvas canvas) {
        if (borderTopDrawable == null && borderBottomDrawable == null) {
            return;
        }

        int saveId = canvas.save();

        if (borderTopDrawable != null) {
            int dy = mView.getScrollY();
            if (borderTopStyle == BorderEnumWrapper.INSIDE) {
                dy += mView.getPaddingTop();
            }
            canvas.translate(0, dy);
            if (isShowingTopBorder()) {
                borderTopDrawable.setBounds(0, 0, canvas.getWidth(), borderTopDrawable.getIntrinsicHeight());
                mBorderView.onDrawBoardTop(borderTopDrawable, canvas);
            }
            canvas.translate(0, -dy);
        }

        if (borderBottomDrawable != null) {
            int dy = mView.getScrollY() + canvas.getHeight() - borderBottomDrawable.getIntrinsicHeight();
            if (borderTopStyle == BorderEnumWrapper.INSIDE) {
                dy -= mView.getPaddingBottom();
            }
            canvas.translate(0, dy);

            if (isShowingBottomBorder()) {
                borderBottomDrawable.setBounds(0, 0, canvas.getWidth(), borderBottomDrawable.getIntrinsicHeight());
                mBorderView.onDrawBoardBottom(borderBottomDrawable, canvas);
            }
        }

        canvas.restoreToCount(saveId);
    }

    public void onBorderVisibilityChanged(boolean top, boolean oldTop, boolean bottom, boolean oldBottom) {
        if (getBorderVisibilityChangedListener() != null) {
            getBorderVisibilityChangedListener().onBorderVisibilityChanged(top, oldTop, bottom, oldBottom);
        }

        this.isShowingTopBorder = top;
        this.isShowingBottomBorder = bottom;

        mView.postInvalidate();
    }

    ////////////// set/get of borderTopDrawable //////////////
    public Drawable getBorderTopDrawable() {
        return borderTopDrawable;
    }

    public void setBorderTopDrawable(Drawable borderTopDrawable) {
        if (borderTopDrawable != this.borderTopDrawable) {
            this.borderTopDrawable = borderTopDrawable;
            mView.postInvalidate();
        }
    }

    ////////////// set/get of borderBottomDrawable //////////////
    public Drawable getBorderBottomDrawable() {
        return borderBottomDrawable;
    }

    public void setBorderBottomDrawable(Drawable borderBottomDrawable) {
        if (borderBottomDrawable != this.borderBottomDrawable) {
            this.borderBottomDrawable = borderBottomDrawable;
            mView.postInvalidate();
        }
    }

    ////////////// set/get of borderTopVisibility //////////////
    @BorderEnumWrapper.BorderVisibility
    public int getBorderTopVisibility() {
        return borderTopVisibility;
    }

    public void setBorderTopVisibility(@BorderEnumWrapper.BorderVisibility int visibility) {
        if (visibility != this.borderTopVisibility) {
            this.borderTopVisibility = visibility;
            mBorderView.updateBorderStatus();
        }
    }

    ////////////// set/get of borderBottomVisibility //////////////
    @BorderEnumWrapper.BorderVisibility
    public int getBorderBottomVisibility() {
        return borderBottomVisibility;
    }

    public void setBorderBottomVisibility(@BorderEnumWrapper.BorderVisibility int visibility) {
        if (visibility != this.borderBottomVisibility) {
            this.borderBottomVisibility = visibility;
            mBorderView.updateBorderStatus();
        }
    }

    ////////////// set/get of BorderTopStyle //////////////
    public @BorderEnumWrapper.BorderStyle int getBorderTopStyle() {
        return borderTopStyle;
    }

    public void setBorderTopStyle(@BorderEnumWrapper.BorderStyle int borderTopStyle) {
        if (this.borderTopStyle != borderTopStyle) {
            this.borderTopStyle = borderTopStyle;
            mView.postInvalidate();
        }
    }

    ////////////// set/get of BorderBottomStyle //////////////
    public @BorderEnumWrapper.BorderStyle int getBorderBottomStyle() {
        return borderBottomStyle;
    }

    public void setBorderBottomStyle(@BorderEnumWrapper.BorderStyle int borderBottomStyle) {
        if (this.borderBottomStyle != borderBottomStyle) {
            this.borderBottomStyle = borderBottomStyle;
            mView.postInvalidate();
        }
    }

    public boolean isShowingTopBorder() {
        if (isShowingTopBorder != null) {
            return isShowingTopBorder;
        } else {
            return borderTopVisibility == BorderEnumWrapper.TOP_OR_BOTTOM || borderTopVisibility == BorderEnumWrapper.ALWAYS;
        }
    }

    public boolean isShowingBottomBorder() {
        if (isShowingBottomBorder != null) {
            return isShowingBottomBorder;
        } else {
            return borderBottomVisibility == BorderEnumWrapper.ALWAYS;
        }
    }

    ////////////// set/get of mBorderVisibilityChangedListener //////////////
    public BorderView.OnBorderVisibilityChangedListener getBorderVisibilityChangedListener() {
        return mBorderVisibilityChangedListener;
    }

    public void setBorderVisibilityChangedListener(BorderView.OnBorderVisibilityChangedListener borderVisibilityChangedListener) {
        mBorderVisibilityChangedListener = borderVisibilityChangedListener;
    }
}
