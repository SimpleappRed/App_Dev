package com.example.nidheesha.first_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent i = getIntent();
        String s = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView e = (TextView)findViewById(R.id.text);
        e.setText(s);
    }
}
