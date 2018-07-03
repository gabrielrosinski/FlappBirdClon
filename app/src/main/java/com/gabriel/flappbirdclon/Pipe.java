package com.gabriel.flappbirdclon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pipe {

    public enum PipeType {
        UPPER, BOTTOM
    }

    protected int horizontalSpeed;

    private Bitmap bitmap;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private int ySpeed = 5;
    private SceneView sceneView;
    public PipeType pipeType = PipeType.UPPER;
    private int space = 350;


    public Pipe(SceneView sceneView, Bitmap bitmap) {
        this.sceneView = sceneView;
        this.bitmap = bitmap;

        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }


    private void update() {
//        if (x > sceneView.getWidth() - width - xSpeed) {
//            xSpeed = -5;
//        }
//        if (x + xSpeed < 0) {
//            xSpeed = 5;
//        }
//        x = x + xSpeed;

    }

    public void draw(Canvas canvas) {
        update();
        int srcX = width;
        int srcY = height;
        Rect src = new Rect(srcX, srcY, bitmap.getWidth(), bitmap.getHeight());
        Rect dst;
        if(this.pipeType == PipeType.UPPER){
            dst = new Rect(200, 0, sceneView.getWidth() / 7 + 200 , sceneView.getBottom() / 2);
        }else {
            dst = new Rect(200, sceneView.getBottom() / 2 + space, sceneView.getWidth() / 7 + 200 , sceneView.getBottom());
        }


        canvas.drawBitmap(bitmap, src, dst, null);
    }
}

//Rect(int left, int top, int right, int bottom)