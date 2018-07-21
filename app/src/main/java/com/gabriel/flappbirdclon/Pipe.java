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


    private void update() {
//        if (x > sceneView.getWidth() - width - xSpeed) {
//            xSpeed = -5;
//        }
//        if (x + xSpeed < 0) {
//            xSpeed = 5;
//        }
//        x = x + xSpeed;



//        if (x < sceneView.getLeft() - width) {
////            this.x = sceneView.getWidth() + pipeNum * (sceneView.getWidth());//sceneView.getRight() + distanceFromOtherTube;
////            this.x = sceneView.getWidth()  + (this.pipeNum * sceneView.getWidth() / 2);
//            this.x = sceneView.getWidth()  + (pipeNum * distanceFromOtherTube);
//        }
//        xSpeed = -5;
//        x = x + xSpeed;

        xSpeed = -5;

        int distanceOffSet = pipeNum * (sceneView.getWidth() / 2);
        if(pipeXOffSet <  sceneView.getLeft() - width){
            pipeXOffSet = sceneView.getWidth() + (sceneView.getWidth() / 2) ;//+ (distanceOffSet);//(int)(pipeNum * distanceFromOtherTube);
            pipeXOffSet = pipeXOffSet + xSpeed;
        }else{
            pipeXOffSet = pipeXOffSet + xSpeed;
        }


    }

    public void draw(Canvas canvas) {
        update();

//        if(this.pipeType == PipeType.UPPER){
//            dst = new Rect(this.x, 0, x + this.width , (sceneView.getHeight() / 2) - (space / 2) + (int)tubeOffset);
//        }else {
//            dst = new Rect(this.x, (sceneView.getHeight() / 2)  + (space / 2) + (int)tubeOffset, x + this.width , sceneView.getBottom());
//        }

        if(this.pipeType == PipeType.UPPER){
            dst = new Rect(pipeXOffSet, 0, pipeXOffSet + this.width , (sceneView.getHeight() / 2) - (space / 2) + (int)tubeOffset);
        }else {
            dst = new Rect(pipeXOffSet, (sceneView.getHeight() / 2)  + (space / 2) + (int)tubeOffset, pipeXOffSet + this.width , sceneView.getBottom());
        }

        canvas.drawBitmap(bitmap, src, dst, null);
    }
}

