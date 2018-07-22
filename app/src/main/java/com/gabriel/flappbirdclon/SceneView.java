package com.gabriel.flappbirdclon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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

    //Game managment
    private int score = 0;
    private int maxPointsToWin = 5;
    private int scoringTube = 0;
    private Boolean gameStateInGame = true;

    private TextPaint scoreText;
    private StaticLayout scoreTextStyle;


    private final Context context;


    public SceneView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SceneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SceneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init(){

        spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.birdmap);
        sprite = new Sprite(this,spritesBitmap);

        topPipeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        bottomPipeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);

        randomGenerator = new Random();

        initLabelView();

    }

    //setup background image
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

        if (gameStateInGame){
            if (!tubesCreated){
                tubesCreated = true;
                tubeCreator(canvas);
            }


            sprite.draw(canvas);

            //draw each pipi
            for (int i = 0; i < numberOfTubes; i++) {
                upperPipes[i].draw(canvas);
                bottomPipes[i].draw(canvas);

            }

            //COLISION DETECTION
            for (int i = 0; i < numberOfTubes; i++) {
                if (sprite.isCollisionDetected(upperPipes[i])){
                    gameStateInGame = false;
                    ((GameActivity) context).moveToEndGame();
                }
                if (sprite.isCollisionDetected(bottomPipes[i])){
                    gameStateInGame = false;
                    ((GameActivity) context).moveToEndGame();
                }
            }



            //Update score
            Pipe pipe = upperPipes[scoringTube];
            if (pipe.getPipeX() < (canvas.getWidth() / 2) - pipe.getPipeWidth()){

                this.score++;

                if (scoringTube < numberOfTubes - 1){
                    this.scoringTube++;
                }else{
                    scoringTube = 0;
                }
            }

            //Score Label edit
            editScoreLabel();
            scoreTextStyle.draw(canvas);
        }

        postInvalidateOnAnimation();
    }

    //init score label
    private void initLabelView() {
        scoreText = new TextPaint();
        scoreText.setAntiAlias(true);
        scoreText.setTextSize(40 * getResources().getDisplayMetrics().density);
        scoreText.setColor(0xFF000000);

        editScoreLabel();

    }

    //update score label
    private void editScoreLabel(){
        // default to a single line of text
        String scoreStr = "Score: " + String.valueOf(score);
        int width = (int) scoreText.measureText(scoreStr);
        scoreTextStyle = new StaticLayout(scoreStr, scoreText, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
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


    //method for tube inital creation
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

}
