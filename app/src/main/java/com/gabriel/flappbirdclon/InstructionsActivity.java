package com.gabriel.flappbirdclon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gabriel.flappbirdclon.Workers.Utillity;

public class InstructionsActivity extends AppCompatActivity {

    TextView instructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Utillity.hideSystemUI(this);
    }
}
