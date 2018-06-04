package com.gabriel.flappbirdclon.Workers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.AndroidException;
import android.view.View;

import SharedUtils.AsyncHandler;

public class Utillity {

    private static Utillity myObj;
    private static Context context;

    private static MusicRunnable musicPlayer;
    private static SFxRunnable soundEffectsUtil;


    public static Utillity getInstance(Context appContext){
        if(myObj == null){

            myObj = new Utillity();

            context = appContext;

            if (musicPlayer == null) {
                musicPlayer = new MusicRunnable(context);

            }

            if (soundEffectsUtil == null) {
                soundEffectsUtil = new SFxRunnable(context);
            }
        }
        return myObj;
    }



    public static void hideSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    public static void toggleMusic(){
        AsyncHandler.post(musicPlayer);
    }

    public static void setBackgroundMusicVolume(int volume){
        musicPlayer.mPlayer.setVolume(volume, volume);
    }

    public static void playSFXbyID(int id){
        soundEffectsUtil.play(id);
    }
}
