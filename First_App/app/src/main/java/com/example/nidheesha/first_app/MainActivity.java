package com.example.nidheesha.first_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    Button b ;
    EditText e;
    static String  EXTRA_MESSAGE = "com.example.nidheesha.First_App.Display";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.button1);
        e = (EditText)findViewById(R.id.text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = e.getText().toString();
                Intent i = new Intent(MainActivity.this,Display.class);
                i.putExtra(EXTRA_MESSAGE,s);
                startActivity(i);
            }
        });
    }

}
