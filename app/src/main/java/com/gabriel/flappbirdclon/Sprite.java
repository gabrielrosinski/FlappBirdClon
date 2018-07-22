package com.gabriel.flappbirdclon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.gabriel.flappbirdclon.Pipe;
import com.gabriel.flappbirdclon.SceneView;

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


    //method to identefy if there was a collision between the sprite and the pipe
    public boolean isCollisionDetected(Pipe pipe) {

        if (pipe == null) {
            Log.d("SceneView","Pipe must not be null");
            throw new IllegalArgumentException("Pipe mut be not null");
        }

        int spritesWidth = (int) (width * 1.7);
        int spritesHight = (int) (height * 1.4);
        Rect spriteRect = new Rect(x, y, spritesWidth, spritesHight);
        Rect pipeRect = pipe.getPipeRect();


        if (pipe.pipeType == pipe.pipeType.UPPER){
            if (spriteRect.right > pipeRect.left && spriteRect.left < pipeRect.left && spriteRect.top < pipeRect.bottom){
                return true;
            }
        }else{

            if (spriteRect.right > pipeRect.left && spriteRect.left < pipeRect.left && spriteRect.top > pipeRect.top){
                return true;
            }
        }

        //Touched the ground - Fail
        if (spriteRect.top > sceneView.getBottom()){
            return true;
        }

        return false;
    }


    public void spriteTouched(){
        this.spriteTouched = true;
    }


    //method that updates Bird location
    private void update() {

        //this is to init the bird in the first position
        if (freshScene){
            x = (sceneView.getWidth() / 2) - (width / 4);
            y = (sceneView.getHeight() / 2) - (height / 2);
            freshScene = false;
        }


        if ( y < sceneView.getTop()){
            y = sceneView.getTop() + 50;
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

    //method to draw the bird
    public void draw(Canvas canvas) {

        update();

        int srcX = currentFrame * width;
        int srcY = 1 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, (x + width / 2) , (y + height / 2));
        canvas.drawBitmap(bitmap, src, dst, null);
    }

}

