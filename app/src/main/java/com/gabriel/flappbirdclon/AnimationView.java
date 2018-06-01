package com.gabriel.flappbirdclon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

public class AnimationView extends android.support.constraint.ConstraintLayout {

    private static final int NUM_FRAMES = 64;
    RectF dst0 = new RectF();
    Rect[] frames = new Rect[NUM_FRAMES];
    Bitmap spritesBitmap;
    Bitmap bgBitmap;
    int mCharHeight;
    int mCharWidth;
    Paint topRectPaint = new Paint();
    int naiveFrameNam;
    private int mViewHeight;
    private int mViewWidth;

    Drawable d;


    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // allow to call onDraw
        setWillNotDraw(false);

        prepareCharacter();

        dst0 = new RectF(frames[0]);
        dst0.offset(10, 10); // like translate for canvas

        topRectPaint.setColor(0xff_dd_dd_ff);
        topRectPaint.setStyle(Paint.Style.STROKE);
    }

    private void prepareCharacter() {
        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.running_grant);
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

//        Drawable d = ContextCompat.getDrawable(this.getContext(), R.drawable.background);
//        d.setBounds(getLeft(), getTop(), getRight(), getBottom());

        // setup the rects
        mCharWidth = (spritesBitmap.getWidth() - 64) / 12;
        mCharHeight = (spritesBitmap.getHeight() - 292) / 6;


        int i = 0; // rect index
        for (int y = 0; y < 6; y++) { // row
            for (int x = 0; x < 12; x++) { // column
                frames[i] = new Rect(x * mCharWidth, y * mCharHeight, (x + 1) * mCharWidth, (y + 1) * mCharHeight);
                i++;
                if (i >= NUM_FRAMES) {
                    break;
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mViewWidth = w;
        this.mViewHeight = h;
//        createBackgroundImage(w, h);

//        for (Character c: ch) {
//            c.measure();
//        }

        super.onSizeChanged(w, h, oldw, oldh);

    }

    Rect a = new Rect(0,0,200,200);
    Rect b = new Rect(0,0,2000,2000);

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(0xffff0000);
//        canvas.drawBitmap(bgBitmap, 0, mViewHeight / 2, null);
//        canvas.drawRect(dst0, topRectPaint);

//        d.draw(canvas);
        canvas.drawBitmap(spritesBitmap, a , b, topRectPaint);

//        canvas.drawBitmap(bgBitmap, 0, mViewHeight, null);


//        for (Character c: ch) {
//            c.draw(canvas); // draw the smaller (farther) first
//        }

//        postInvalidateDelayed(60); // slower = more natural movement
//        postInvalidateOnAnimation(); // too fast
    }


}
