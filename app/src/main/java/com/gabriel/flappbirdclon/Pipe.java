package com.gabriel.flappbirdclon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Pipe {

    public enum PipeType {
        UPPER, BOTTOM
    }

    private Bitmap bitmap;
    private int currentFrame = 0;
    private int width;
    private int height;
    public int x = 0;
    private int xSpeed = 5;
    private int ySpeed = 5;
    private SceneView sceneView;
    public PipeType pipeType = PipeType.UPPER;
    public int space = 350;

    private int pipeWidth = 170;
    private float distanceFromOtherTube;
    private int pipeXOffSet;
    private int pipeNum;
    private Rect src;
    private Rect dst;

    private float tubeOffset;

    public Pipe(SceneView sceneView, Bitmap bitmap,float randomNum, int pipeNum) {

        this.sceneView = sceneView;
        this.bitmap = bitmap;

        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        this.width =  pipeWidth;
        this.height = bitmap.getHeight();
        this.pipeNum = pipeNum;

        this.distanceFromOtherTube = (sceneView.getWidth() / 2) + (sceneView.getWidth() / 4) ;

        this.pipeXOffSet = sceneView.getWidth() + Math.round(pipeNum * distanceFromOtherTube);

        tubeOffset = (randomNum - 0.5f) * (sceneView.getHeight() - space - 200);

    }

    public Rect getPipeRect(){
        return this.dst;
    }


    private void update() {


        xSpeed = -5;

//        int distanceOffSet = pipeNum * (sceneView.getWidth() / 2);

        if(pipeXOffSet <  sceneView.getLeft() - width){
            pipeXOffSet = sceneView.getWidth() + (sceneView.getWidth() / 2) ;
            pipeXOffSet = pipeXOffSet + xSpeed;
        }else{
            pipeXOffSet = pipeXOffSet + xSpeed;
        }


    }

    public void draw(Canvas canvas) {

        update();


        if(this.pipeType == PipeType.UPPER){
            dst = new Rect(pipeXOffSet, 0, pipeXOffSet + this.width , (sceneView.getHeight() / 2) - (space / 2) + (int)tubeOffset);
        }else {
            dst = new Rect(pipeXOffSet, (sceneView.getHeight() / 2)  + (space / 2) + (int)tubeOffset, pipeXOffSet + this.width , sceneView.getBottom());
        }

        canvas.drawBitmap(bitmap, src, dst, null);
    }
}

