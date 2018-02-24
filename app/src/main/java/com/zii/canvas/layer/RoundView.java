package com.zii.canvas.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.zii.canvas.R;

/**
 * @create Created by Zii on 2018/2/23.
 */

public class RoundView extends android.support.v7.widget.AppCompatImageView {
    private RectF mRound = new RectF();
    private Paint mStrokePaint = new Paint();
    private Paint mZoomPaint = new Paint();
    private Paint mMaskPaint = new Paint();
    private Bitmap mBitmap;

    public RoundView(Context context) {
        super(context);
        init();
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBitmap = zoomImg(mBitmap, ((int) mRound.width()), (int) mRound.height());

        init();
    }

    private void init() {
        setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));

        mStrokePaint.setAntiAlias(true);
//        mStrokePaint.setColor(Color.BLUE);
//        mStrokePaint.setStyle(Paint.Style.STROKE);

        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mZoomPaint.setAntiAlias(true);
        mZoomPaint.setColor(Color.WHITE);

        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_setting_gender_man);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRound.set(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mRound, mZoomPaint, Canvas.ALL_SAVE_FLAG);//创建圆形图层
        canvas.drawRoundRect(mRound, mRound.centerX(), mRound.centerY(), mZoomPaint);
        canvas.saveLayer(mRound, mMaskPaint, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);//绘制原图
        canvas.restore();

        canvas.drawBitmap(mBitmap, 0, 0, mStrokePaint);
    }

    private Bitmap zoomImg(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
