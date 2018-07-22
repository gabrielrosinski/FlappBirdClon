package com.gabriel.flappbirdclon;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gabriel.flappbirdclon.Workers.Utillity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utillity.getInstance(getApplicationContext());

        Utillity.hideSystemUI(this);

        if (!Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }



        //Button clicked to start new game
        Button startGameBtn = findViewById(R.id.startBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GameActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });



        //button clicked to go to the settings
        Button settingsBtn = findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });


        //button clicked to go to game instructions
        Button instructionBtn = findViewById(R.id.instructionBtn);

        instructionBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
//        Utillity.toggleMusic();

    }

    @Override
    protected void onRestart(){
        super.onRestart();
//        Utillity.toggleMusic();
        Utillity.hideSystemUI(this);

    }


    @Override
    protected void onStop(){
        super.onStop();
//        Utillity.toggleMusic();
    }


}
