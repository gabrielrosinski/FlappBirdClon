package com.gabriel.flappbirdclon;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utillity.hideSystemUI(this);


        Button startGameBtn = findViewById(R.id.startBtn);


        startGameBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GameActivity.class);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.abc_fade_in, R.anim.abc_fade_out);

                startActivity(intent, options.toBundle());
            }
        });
    }


}
