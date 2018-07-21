package com.gabriel.flappbirdclon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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

    //Game managment
    private int score = 0;
    private int maxPointsToWin = 5;
    private int scoringTube = 0;

    private TextPaint scoreText;
    private StaticLayout scoreTextStyle;


    public SceneView(Context context) {
        super(context);
        init();
    }

    public SceneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
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


        Log.d("SceneView", "PLAYER LOST");
        initLabelView();

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



        //SCORE
        Pipe pipe = upperPipes[scoringTube];
        if (pipe.getPipeX() < (canvas.getWidth() / 2) - pipe.getPipeWidth()){

            this.score++;
//            this.scoreTextView.setText(this.score);

            if (scoringTube < numberOfTubes - 1){
                this.scoringTube++;
            }else{
                scoringTube = 0;
            }
        }

        //Score Label
        editScoreLabel();
        scoreTextStyle.draw(canvas);


        postInvalidateOnAnimation();
    }

    private void initLabelView() {
        scoreText = new TextPaint();
        scoreText.setAntiAlias(true);
        scoreText.setTextSize(40 * getResources().getDisplayMetrics().density);
        scoreText.setColor(0xFF000000);

        editScoreLabel();

        // New API alternate
        //
        // StaticLayout.Builder builder = StaticLayout.Builder.obtain(mText, 0, mText.length(), mTextPaint, width)
        //        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
        //        .setLineSpacing(1, 0) // multiplier, add
        //        .setIncludePad(false);
        // mStaticLayout = builder.build();
    }

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
