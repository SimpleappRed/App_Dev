package com.example.nidheesha.calculator_basic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Display extends AppCompatActivity {
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        answer = (TextView)findViewById(R.id.answer);
        Intent i = getIntent();
        String s = i.getStringExtra(MainActivity.EXTRA);
        answer.setText(s);
    }
}
