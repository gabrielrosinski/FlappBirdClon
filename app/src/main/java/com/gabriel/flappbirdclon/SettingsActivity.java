package com.gabriel.flappbirdclon;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.gabriel.flappbirdclon.Workers.Utillity;

import SharedUtils.Util;


public class SettingsActivity extends AppCompatActivity {

    private static SeekBar volumeSeekBar;
    private static ImageView muteImage;

    private boolean isSFXOn = false;
    private CheckBox sfxCheckBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Utillity.hideSystemUI(this);

        if (!Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }


        volumeSeekBar = findViewById(R.id.volumeSeekBar);

        SharedPreferences pref=  Utillity.getSharedPref();
        int volume = pref.getInt("bgVoluem",1);

        volumeSeekBar.setProgress(volume);


        isSFXOn = pref.getBoolean("sfxOn",false);
        sfxCheckBox = findViewById(R.id.sfxCheckBox);
        sfxCheckBox.setChecked(isSFXOn);



        muteImage = findViewById(R.id.muteImage);

        if (volume != 0) {
            muteImage.setVisibility(View.INVISIBLE);
//            Utillity.toggleMusic();
        }else{
            muteImage.setVisibility(View.VISIBLE);
        }

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Utillity.setBackgroundMusicVolume(progress);

                 if (progress == 0) {
                     muteImage.setVisibility(View.VISIBLE);
                     Utillity.toggleMusic();
                 }else{
                     muteImage.setVisibility(View.INVISIBLE);
                     if (!Utillity.isBackgroundMusicON()){
                         Utillity.toggleMusic();
                     }
                 }

                 //Save to pref
                SharedPreferences.Editor prefEdit = Utillity.getSharedPrefEdit();
                prefEdit.putInt("bgVoluem", progress);
                prefEdit.apply();
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

    @Override
    protected void onResume(){
        super.onResume();

        if(!Utillity.isBackgroundMusicON()){
            Utillity.toggleMusic();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(!Utillity.isBackgroundMusicON()){
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



    public void sfxClicked(View view){

        CheckBox checkBox = (CheckBox) view;

        isSFXOn = checkBox.isChecked();

        SharedPreferences.Editor prefEdit = Utillity.getSharedPrefEdit();
        prefEdit.putBoolean("sfxOn", isSFXOn);
        prefEdit.apply();

    }

    public void sfxTestClicked(View v){

//        Button testBtn = findViewById(R.id.sfxTestBTN);

        if (isSFXOn){
            Utillity.playSFXbyID(R.raw.door_lock);
        }

    }



}
