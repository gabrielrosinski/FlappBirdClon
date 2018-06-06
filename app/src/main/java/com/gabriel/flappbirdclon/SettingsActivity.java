package com.gabriel.flappbirdclon;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.gabriel.flappbirdclon.Workers.Utillity;


public class SettingsActivity extends AppCompatActivity {

    private static SeekBar volumeSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Utillity.hideSystemUI(this);

//        Utillity.toggleMusic();

        volumeSeekBar = findViewById(R.id.volumeSeekBar);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress_value = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 Utillity.setBackgroundMusicVolume(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        Button backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(SettingsActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });
    }



    public void sfxClicked(View view){

        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()){
            Utillity.toggleMusic();//.toggleSFC();
        }
    }



}
