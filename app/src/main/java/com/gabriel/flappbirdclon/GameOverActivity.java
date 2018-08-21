package com.gabriel.flappbirdclon;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gabriel.flappbirdclon.Workers.Utillity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);


        //Button clicked to play again
        Button playAgainBtn = findViewById(R.id.playAgainBtn);

        playAgainBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(GameOverActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });



        //Button clicked to open main menu
        Button mainMenuBtn = findViewById(R.id.mainMenuBtn);

        mainMenuBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(GameOverActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);

    }


//    @Override
//    protected void onStop(){
//        super.onStop();
//        Utillity.toggleMusic();
//    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Utillity.toggleMusic();
    }

}
