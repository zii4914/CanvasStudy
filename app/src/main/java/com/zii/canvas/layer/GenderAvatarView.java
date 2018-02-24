package com.zii.canvas.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.UiThread;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zii.canvas.R;

/**
 * 带性别覆盖层的原型头像控件
 */
public class GenderAvatarView extends SimpleDraweeView {

    private Paint mDstPaint;
    private Paint mSrcPaint;
    private Paint mOverlayPaint;

    private Bitmap mBitmapFemale;
    private Bitmap mBitmapMale;
    private Bitmap mBitmapGender;

    private RectF mRoundRectF;
    private boolean mIsMale;

    private int mWidth;
    private int mHeight;

    public GenderAvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderAvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDstPaint = new Paint();
        mDstPaint.setAntiAlias(true);

        mSrcPaint = new Paint();
        mSrcPaint.setAntiAlias(true);
        mSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//重点

        mOverlayPaint = new Paint();
        mOverlayPaint.setAntiAlias(true);

        mBitmapFemale = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_setting_gender_lady);
        mBitmapMale = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_setting_gender_man);

        mRoundRectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initGender(mIsMale, w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();
        mRoundRectF.set(0, 0, mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mRoundRectF, mDstPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(mRoundRectF, mRoundRectF.centerX(), mRoundRectF.centerY(), mDstPaint);//dst，圆形区域

        canvas.saveLayer(mRoundRectF, mSrcPaint, Canvas.ALL_SAVE_FLAG);//paint使用src_in模式，与上一次相交保留src显示
        super.onDraw(canvas);//src，头像
        canvas.restore();

        canvas.drawBitmap(mBitmapGender, 0, 0, mOverlayPaint);//overlay
    }

    private Bitmap zoomBitmap(Bitmap src, int dstWidth, int dstHeight) {
        if (src == null || (src.getWidth() == dstWidth && src.getHeight() == dstHeight)) {
            return src;
        }

        return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
    }

    @UiThread
    public void setGender(boolean isMale) {
        this.mIsMale = isMale;
        initGender(mIsMale, mWidth, mHeight);
        invalidate();
    }

    private void initGender(boolean isMale, int w, int h) {
        if (w == 0 || h == 0) {
            return;
        }
        mBitmapGender = isMale ? mBitmapMale : mBitmapFemale;
        mBitmapGender = zoomBitmap(mBitmapGender, w, h);
    }
}
