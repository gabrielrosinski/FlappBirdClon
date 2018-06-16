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
import android.view.MotionEvent;
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


    private void init(){

//        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable);
        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.birdmap);
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





//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN ||
//                event.getAction() == MotionEvent.ACTION_MOVE) {
//            touchedX = event.getX();
//            touchedY = event.getY();
//            isTouched = true;
//            postInvalidateOnAnimation();
//            return true;
//        } else {
//            isTouched = false;
//        }
//        // will trigger a new call to onDraw()
//        postInvalidateOnAnimation();
//        return super.onTouchEvent(event);
//    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public class Sprite {


        protected static final int GRAVITY = 1;
        protected static final int MAX_DROP_SPEED = 12;
        protected int acceleration = GRAVITY;
        protected int verticalSpeed;

        private Bitmap bitmap;
        private int currentFrame = 0;
        private int width;
        private int height;
//        private static final int BMP_ROWS = 6;
//        private static final int BMP_COLUMNS = 12;
        private int x = 0;
        private int y = 0;
        private int xSpeed = 5;
        private int ySpeed = 5;
        private SceneView sceneView;
        private boolean freshScene = true;


        private static final int BMP_ROWS = 3;
        private static final int BMP_COLUMNS = 3;



        public Sprite(SceneView sceneView, Bitmap bitmap) {
            this.sceneView = sceneView;
            this.bitmap = bitmap;
//            this.width = (bitmap.getWidth() - 64) / 12;
//            this.height = (bitmap.getHeight() - 292) / 6;


            this.width = (bitmap.getWidth() ) / BMP_COLUMNS;
            this.height = (bitmap.getHeight() ) / BMP_ROWS;

        }


        private void update() {
            //this is to init the bird in the first position
            if (freshScene){
                x = (sceneView.getWidth() / 2) - (width / 4);
                y = (sceneView.getHeight() / 2) - (height / 2);
                freshScene = false;
            }

//            if (x > sceneView.getWidth() - width - xSpeed) {
//                xSpeed = -5;
//            }
//            if (x + xSpeed < 0) {
//                xSpeed = 5;
//            }
//            x = x + xSpeed;

            // now calculate the new speed
            acceleration += GRAVITY; // always applying gravity to current acceleration
            verticalSpeed += acceleration; // always applying the current acceleration tp the current speed
            verticalSpeed = Math.min(verticalSpeed, MAX_DROP_SPEED); // but capping it to a terminal velocity (science bitch)

            if (y > sceneView.getBottom() - height - ySpeed){
                y = (sceneView.getHeight() / 2) - (height / 2);
            }
            y = y + verticalSpeed;

            currentFrame = (currentFrame + 1) % BMP_COLUMNS;
        }

        public void draw(Canvas canvas) {

            update();

            int srcX = currentFrame * width;
            int srcY = 1 * height;
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect(x, y, (x + width / 2) , (y + height / 2));
            canvas.drawBitmap(bitmap, src, dst, null);
        }

    }




}
