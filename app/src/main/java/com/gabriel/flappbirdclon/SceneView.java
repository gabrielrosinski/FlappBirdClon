package com.gabriel.flappbirdclon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gabriel.flappbirdclon.Workers.Sprite;

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
        //COLISON DETECTION
        for (int i = 0; i < numberOfTubes; i++) {
           if (sprite.isCollisionDetected(upperPipes[i])){
               Log.d("SceneView", "PLAYER LOST");
           }
           if (sprite.isCollisionDetected(bottomPipes[i])){
                Log.d("SceneView", "PLAYER LOST");
           }

        }

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




}
