package com.gabriel.flappbirdclon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.gabriel.flappbirdclon.Workers.Utillity;

import SharedUtils.Util;

public class SettingsActivity extends AppCompatActivity {

    private static SeekBar volumeSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Utillity.hideSystemUI(this);

        Utillity.toggleMusic();

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
//        volumeSeekBar.setMax(100);
//        volumeSeekBar.setProgress(10);

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

    }
}
