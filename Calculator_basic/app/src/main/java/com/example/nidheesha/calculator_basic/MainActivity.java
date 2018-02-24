package com.example.nidheesha.calculator_basic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText number1,number2;
    RadioButton add,sub;
    Button submit;
    static String EXTRA = "com.example.nidheesha.Calculator_basic.Display";
    String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number1 = (EditText)findViewById(R.id.number1);
        number2 = (EditText)findViewById(R.id.number2);
        add = (RadioButton)findViewById(R.id.add);
        sub = (RadioButton)findViewById(R.id.sub);
        submit = (Button)findViewById(R.id.submit);
        number1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                add.setChecked(false);
                sub.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        number2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                add.setChecked(false);
                sub.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub.setChecked(false);

                if(number1.getText().toString().equals("") || number2.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"ENTER TWO NUMBERS",Toast.LENGTH_LONG).show();
                    return;
                }
                Integer a = Integer.parseInt(number1.getText().toString())+Integer.parseInt(number2.getText().toString());
                answer = a.toString();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add.setChecked(false);

                if(number1.getText().toString().equals("") || number2.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"ENTER TWO NUMBERS",Toast.LENGTH_LONG).show();
                    return;
                }
                Integer a = Integer.parseInt(number1.getText().toString())-Integer.parseInt(number2.getText().toString());
                answer = a.toString();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number1.getText().toString().equals("") || number2.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"ENTER TWO NUMBERS",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this,Display.class);
                i.putExtra(EXTRA,answer);
                startActivity(i);
            }
        });
    }
}
