package com.gabriel.flappbirdclon.Workers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AndroidException;
import android.view.View;

import SharedUtils.AsyncHandler;

public class Utillity {

    private final static int MAX_VOLUME = 100;

    private static Utillity myObj;
    private static Context context;

    private static MusicRunnable musicPlayer;
    private static SFxRunnable soundEffectsUtil;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor preferencesEditor;
    private static String MY_PREFS_NAME = "birdSharedPref";

    public static Utillity getInstance(Context appContext){
        if(myObj == null){

            myObj = new Utillity();

            context = appContext;

            sharedPreferences = appContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            preferencesEditor = sharedPreferences.edit();

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


    //Methods that deal with apps background music
    public static void toggleMusic(){
        AsyncHandler.post(musicPlayer);
    }

    public static boolean isBackgroundMusicON(){
        return musicPlayer.isMusicIsPlaying();
    }

    public static void setBackgroundMusicVolume(int volume){

        final float soundVolume = (float) (1 - (Math.log(MAX_VOLUME - volume) / Math.log(MAX_VOLUME)));
        musicPlayer.mPlayer.setVolume(soundVolume, soundVolume);
    }


    //Methods that dael with apps SFX
    public static void playSFXbyID(int id){
        soundEffectsUtil.play(id);
    }

    public  void toggleSFX(int streamID, boolean toggle){
        if (toggle){
            soundEffectsUtil.soundPool.setVolume(streamID, 1f, 1f);
        }else{
            soundEffectsUtil.soundPool.setVolume(streamID, 0f, 0f);
        }
    }


    //Methods that deal with apps shared pref
    public static SharedPreferences getSharedPref(){
        return sharedPreferences;
    }
    public static SharedPreferences.Editor getSharedPrefEdit(){
        return preferencesEditor;
    }
}
