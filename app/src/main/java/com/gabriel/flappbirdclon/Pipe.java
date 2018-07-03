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
    public int space = 200;

    public float tubeOffest;
    private int pipeWidth = 170;
    public int pipeNum;
    public int distanceFromOtherTube;
    private Rect src;
    private Rect dst;


    public Pipe(SceneView sceneView, Bitmap bitmap) {
        this.sceneView = sceneView;
        this.bitmap = bitmap;

        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());


        this.width =  pipeWidth;
        this.height = bitmap.getHeight();

        this.x = sceneView.getWidth() + pipeNum * (sceneView.getWidth());//sceneView.getRight() + distanceFromOtherTube;
    }


    private void update() {
//        if (x > sceneView.getWidth() - width - xSpeed) {
//            xSpeed = -5;
//        }
//        if (x + xSpeed < 0) {
//            xSpeed = 5;
//        }
//        x = x + xSpeed;

        if (x < sceneView.getLeft() - width) {
            this.x = sceneView.getWidth() + pipeNum * (sceneView.getWidth());//sceneView.getRight() + distanceFromOtherTube;
        }
        xSpeed = -5;
        x = x + xSpeed;

    }

    public void draw(Canvas canvas) {
        update();

        if(this.pipeType == PipeType.UPPER){
            dst = new Rect(this.x, 0, x + this.width , sceneView.getBottom() / 2 - space + (int)tubeOffest);
        }else {
            dst = new Rect(this.x, sceneView.getBottom() / 2  + space + (int)tubeOffest, x + this.width , sceneView.getBottom());
        }

        canvas.drawBitmap(bitmap, src, dst, null);
    }
}

