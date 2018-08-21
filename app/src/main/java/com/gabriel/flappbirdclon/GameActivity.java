package com.gabriel.flappbirdclon;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.gabriel.flappbirdclon.Workers.Utillity;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);

    }


    @Override
    protected void onStop(){
        super.onStop();
        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);
    }



    void moveToEndGame(){
        Intent intent = new Intent(this, GameOverActivity.class);

        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in, R.anim.abc_fade_out);

        startActivity(intent, options.toBundle());
    }

}
