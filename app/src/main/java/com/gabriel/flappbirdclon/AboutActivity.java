package com.gabriel.flappbirdclon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gabriel.flappbirdclon.Workers.Utillity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (!Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }

        Utillity.hideSystemUI(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if (Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }
        Utillity.hideSystemUI(this);

    }


    @Override
    protected void onPause(){
        super.onPause();
        if (Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }
    }
}
