package com.gabriel.flappbirdclon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.nio.charset.CharacterCodingException;

public class SceneView extends View {


    //Bird sprite
    private static final int NUM_FRAMES = 64;
    int mCharHeight;
    int mCharWidth;
    Bitmap spritesBitmap;
    Bitmap bgBitmap;
    Rect[] frames = new Rect[NUM_FRAMES];
    int naiveFrameNam;
    RectF dst0 = new RectF();
    private int mViewHeight;
    private int mViewWidth;

    private Sprite sprite;
    Rect src;
    Rect dst;


    public SceneView(Context context) {
        super(context);
        init();
    }

    public SceneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SceneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mViewWidth = w;
        this.mViewHeight = h;
        createBackgroundImage(w, h);


        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {




        canvas.drawBitmap(bgBitmap,src, dst,null);

        sprite.draw(canvas);

        postInvalidateOnAnimation();
    }


    private void init(){

        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.running_grant);
        sprite = new Sprite(this,spritesBitmap);


    }

    private void createBackgroundImage(int w, int h) {


        if (bgBitmap != null) {
            bgBitmap.recycle();
        }

        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        src = new Rect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        dst = new Rect(0, 0, getRight(), getBottom());

    }



    public class Sprite {

        private Bitmap bitmap;
        private int currentFrame = 0;
        private int width;
        private int height;
        private static final int BMP_ROWS = 6;
        private static final int BMP_COLUMNS = 12;
        private int x = 0;
        private int y = 0;
        private int xSpeed = 5;
        private SceneView sceneView;


        public Sprite(SceneView sceneView, Bitmap bitmap) {
            this.sceneView = sceneView;
            this.bitmap = bitmap;
            this.width = (bitmap.getWidth() - 64) / 12;
            this.height = (bitmap.getHeight() - 292) / 6;
        }


        private void update() {
            if (x > sceneView.getWidth() - width - xSpeed) {
                xSpeed = -5;
            }
            if (x + xSpeed < 0) {
                xSpeed = 5;
            }
            x = x + xSpeed;
//            y = y + xSpeed;

            currentFrame = (currentFrame + 1) % BMP_COLUMNS;
        }

        public void draw(Canvas canvas) {
            update();
            int srcX = currentFrame * width;
            int srcY = 1 * height;
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect(x, y, x + width, y + height);
            canvas.drawBitmap(bitmap, src, dst, null);
        }



    }




}
