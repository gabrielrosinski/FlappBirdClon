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
import java.util.Random;

public class SceneView extends View {


    //Bird sprite
    private static final int NUM_FRAMES = 64;
    Bitmap spritesBitmap;
    Bitmap bgBitmap;
    Bitmap topPipeBitmap;
    Bitmap bottomPipeBitmap;
    Rect[] frames = new Rect[NUM_FRAMES];
    int naiveFrameNam;
    RectF dst0 = new RectF();
    private int mViewHeight;
    private int mViewWidth;

    private Sprite sprite;
    private Pipe topPipe;
    private Pipe bottomPipe;
    Rect src;
    Rect dst;
    Random randomGenerator;
    float maxTubeOffest;

    int numberOfTubes = 4;
    int distanceBetweenTubes;

    Pipe[] upperPipes = new Pipe[numberOfTubes];
    Pipe[] bottomPipes = new Pipe[numberOfTubes];



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
        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.birdmap);
        sprite = new Sprite(this,spritesBitmap);

        topPipeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        bottomPipeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube); //TODO: change this bottomtube

//        distanceBetweenTubes = this.getWidth() / 2;

        randomGenerator = new Random();


        tubeCreator();
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

//        topPipe.draw(canvas);
//        bottomPipe.draw(canvas);

        for (int i = 0; i < numberOfTubes; i++) {
            upperPipes[i].draw(canvas);
            bottomPipes[i].draw(canvas);
        }


        //TODO: add collision detection later for the sprite




        postInvalidateOnAnimation();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {// ||
//                event.getAction() == MotionEvent.ACTION_MOVE) {
//            touchedX = event.getX();
//            touchedY = event.getY();
//            isTouched = true;
            sprite.spriteTouched();
            postInvalidateOnAnimation();
            return true;
        } else {
//            isTouched = false;
        }
        // will trigger a new call to onDraw()
        postInvalidateOnAnimation();
        return super.onTouchEvent(event);
    }



    private void tubeCreator(){

        float tubeOffset;
        //maxTubeOffest = this.getBottom() / 2 - topPipe.space / 2 - 100;



        for (int i = 0; i < numberOfTubes; i++){
            Pipe topPipe = new Pipe(this,topPipeBitmap);
//        topPipe = new Pipe(this,topPipeBitmap);
//            topPipe.x = this.getRight() + i * distanceBetweenTubes * 100;
//            topPipe.distanceFromOtherTube = this.getWidth() + i * (this.getWidth() / 2);
            topPipe.pipeNum = i;
            topPipe.pipeType = Pipe.PipeType.UPPER;

            tubeOffset = (randomGenerator.nextFloat() - 0.5f) * (this.getHeight() - topPipe.space);

            topPipe.tubeOffest = tubeOffset;
            upperPipes[i] = topPipe;


            Pipe bottomPipe = new Pipe(this,bottomPipeBitmap); //TODO: change this asset to bottom tube

//        bottomPipe = new Pipe(this,topPipeBitmap); //TODO: change this asset to bottom tube
//            bottomPipe.x = this.getWidth() / 2 + i * distanceBetweenTubes; //this.getRight() + i * distanceBetweenTubes * 100;
//            bottomPipe.distanceFromOtherTube = this.getWidth() + i * (this.getWidth() / 2);
            bottomPipe.pipeType = Pipe.PipeType.BOTTOM;
            bottomPipe.tubeOffest = tubeOffset;
            bottomPipe.pipeNum = i;
            bottomPipes[i] = bottomPipe;
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public class Sprite {


        protected static final int GRAVITY = 1;
        protected static final int MAX_DROP_SPEED = 12;
        protected static final int MAX_JUMP_SPEED = -12;
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
        private boolean spriteTouched = false;


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

        public void spriteTouched(){
            this.spriteTouched = true;
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


            if (acceleration < MAX_DROP_SPEED){
                // now calculate the new speed
                acceleration += GRAVITY; // always applying gravity to current acceleration
            }




//            verticalSpeed += acceleration; // always applying the current acceleration tp the current speed
//            verticalSpeed = Math.min(verticalSpeed, MAX_DROP_SPEED); // but capping it to a terminal velocity (science bitch)

            if (y > sceneView.getBottom() || y < sceneView.getTop()){
                //TODO: stop game - lose
                y = (sceneView.getHeight() / 2) - (height / 2);
            }


            if (spriteTouched){

                if (acceleration > -15){
                    acceleration = -7;
                }

                spriteTouched = false;
            }

            verticalSpeed += acceleration; // always applying the current acceleration tp the current speed
            verticalSpeed = Math.min(verticalSpeed, MAX_DROP_SPEED); // but capping it to a terminal velocity (science bitch)
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
