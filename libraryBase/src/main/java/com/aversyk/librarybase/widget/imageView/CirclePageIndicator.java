package com.aversyk.librarybase.widget.imageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.aversyk.librarybase.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


/**
 * 自定义的圆形的提示器
 *
 * @author Aversyk
 * Draws circles (one for each view). The current view position is filled and others are only stroked.
 */
public class CirclePageIndicator extends View {

    private float mRadius;
    private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
    private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
    private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);
    private int mCurrentPage;
    private boolean mCentered;
    private int totalCount;

    //distance between circles
    private float mSpacing;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, 0);
        final int defaultPageColor = a.getColor(R.styleable.CirclePageIndicator_circlePageIndicatorPageColor, Color.GRAY);
        final int defaultFillColor = a.getColor(R.styleable.CirclePageIndicator_circlePageIndicatorFillColor, Color.RED);
        final int defaultStrokeColor = a.getColor(R.styleable.CirclePageIndicator_circlePageIndicatorStrokeColor, Color.GRAY);
        final float defaultStrokeWidth = a.getDimensionPixelSize(R.styleable.CirclePageIndicator_circlePageIndicatorStrokeWidth, 1);
        final float defaultRadius = a.getDimensionPixelSize(R.styleable.CirclePageIndicator_circlePageIndicatorRadius, 5);
        final boolean defaultCentered = a.getBoolean(R.styleable.CirclePageIndicator_circlePageIndicatorCentered, true);
        a.recycle();

        mCentered = defaultCentered;
        mPaintPageFill.setStyle(Style.FILL);
        mPaintPageFill.setColor(defaultPageColor);
        mPaintStroke.setStyle(Style.STROKE);
        mPaintStroke.setColor(defaultStrokeColor);
        mPaintStroke.setStrokeWidth(defaultStrokeWidth);
        mPaintFill.setStyle(Style.FILL);
        mPaintFill.setColor(defaultFillColor);
        mRadius = defaultRadius;
        mSpacing = mRadius;
    }

    public void setCentered(boolean centered) {
        mCentered = centered;
        invalidate();
    }

    public boolean isCentered() {
        return mCentered;
    }

    public void setPageColor(int pageColor) {
        mPaintPageFill.setColor(pageColor);
        invalidate();
    }

    public int getPageColor() {
        return mPaintPageFill.getColor();
    }

    public void setFillColor(int fillColor) {
        mPaintFill.setColor(fillColor);
        invalidate();
    }

    public int getFillColor() {
        return mPaintFill.getColor();
    }

    public void setStrokeColor(int strokeColor) {
        mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor() {
        return mPaintStroke.getColor();
    }

    public void setStrokeWidth(float strokeWidth) {
        mPaintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getStrokeWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }

    public void setSpacing(float spacing) {
        this.mSpacing = spacing;
    }

    public float getSpacing() {
        return mSpacing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int count = totalCount;
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        int width;
        int paddingLeft;
        int paddingRight;
        int paddingTop;
        width = getWidth();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();

//		final float threeRadius = mRadius * 3;
        final float threeRadius = 2 * mRadius + mSpacing;
        final float shortOffset = paddingTop + mRadius;
        float longOffset = paddingLeft + mRadius;
        if (mCentered) {
//			float offset = ((width - paddingLeft - paddingRight) / 2.0f)
//					- (3 * count - 1) * mRadius / 2;
            float offset = ((width - paddingLeft - paddingRight) / 2.0f)
                    - (count * (2 * mRadius + mSpacing) - mSpacing) / 2;
            longOffset += offset;
        }

        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }

        // Draw stroked circles
        for (int iLoop = 0; iLoop < count; iLoop++) {
            float drawLong = longOffset + (iLoop * threeRadius);
            dX = drawLong;
            dY = shortOffset;
            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
            }
        }

        // Draw the filled circle according to the current scroll
        float cx = mCurrentPage * threeRadius;
        dX = longOffset + cx;
        dY = shortOffset;
        canvas.drawCircle(dX, dY, mRadius, mPaintFill);
    }

    public void setCurrentItem(int item) {
        mCurrentPage = item;
        invalidate();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec),
                measureShort(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Calculate the width according the views count
            final int count = totalCount;
            float length = 0;
            if (count > 0) {
//				length =  count * 3 * mRadius  -  mRadius;
                length = count * (2 * mRadius + mSpacing) - mSpacing;
            }
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + length + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the height
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
