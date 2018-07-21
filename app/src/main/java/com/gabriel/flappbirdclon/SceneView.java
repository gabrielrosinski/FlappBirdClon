package com.gabriel.flappbirdclon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class SceneView extends View {


    //Bird sprite
    private static final int NUM_FRAMES = 64;
    Bitmap spritesBitmap;
    Bitmap bgBitmap;
    Bitmap topPipeBitmap;
    Bitmap bottomPipeBitmap;
    Rect[] frames = new Rect[NUM_FRAMES];
    private int mViewHeight;
    private int mViewWidth;

    private Sprite sprite;
    Rect src;
    Rect dst;
    Random randomGenerator;
    int numberOfTubes = 2;
    int distanceBetweenTubes;
    Pipe[] upperPipes = new Pipe[numberOfTubes];
    Pipe[] bottomPipes = new Pipe[numberOfTubes];
    Boolean tubesCreated = false;



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

        if (!tubesCreated){
            tubesCreated = true;
            tubeCreator(canvas);
        }


        sprite.draw(canvas);

        for (int i = 0; i < numberOfTubes; i++) {
            upperPipes[i].draw(canvas);
            bottomPipes[i].draw(canvas);
        }


        //TODO: add collision detection later for the sprite with pipes and floore



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



    private void tubeCreator(Canvas canvas){

        float randomNum;

        for (int i = 0; i < numberOfTubes; i++){

            randomNum = randomGenerator.nextFloat();
            distanceBetweenTubes = canvas.getWidth() / 2 * i;


            Pipe topPipe = new Pipe(this,topPipeBitmap, randomNum,i);
            topPipe.pipeType = Pipe.PipeType.UPPER;
            upperPipes[i] = topPipe;


            Pipe bottomPipe = new Pipe(this,bottomPipeBitmap, randomNum,i);
            bottomPipe.pipeType = Pipe.PipeType.BOTTOM;
            bottomPipes[i] = bottomPipe;
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public class Sprite {


        protected static final int GRAVITY = 1;
        protected static final int MAX_DROP_SPEED = 12;
        protected static final int MAX_JUMP_SPEED = -12;


        private Bitmap bitmap;
        private int currentFrame = 0;
        private int width;
        private int height;
        private int x = 0;
        private int y = 0;
        private SceneView sceneView;
        private boolean freshScene = true;
        private boolean spriteTouched = false;

        private static final int BMP_ROWS = 3;
        private static final int BMP_COLUMNS = 3;
        int velocity = 0;




        public Sprite(SceneView sceneView, Bitmap bitmap) {
            this.sceneView = sceneView;
            this.bitmap = bitmap;

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


            if (y > sceneView.getBottom() || y < sceneView.getTop()){
                //TODO: stop game - lose
                y = (sceneView.getHeight() / 2) - (height / 2);
            }


            if (velocity < MAX_DROP_SPEED){
                velocity++;
            }

            if (spriteTouched){

                if ( MAX_JUMP_SPEED < velocity){
                    velocity = -12;
                }

                spriteTouched = false;
            }

            y += velocity;

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
