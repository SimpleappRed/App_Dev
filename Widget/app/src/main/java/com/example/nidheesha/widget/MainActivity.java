package com.example.nidheesha.widget;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button add,update,view,delete;
    TextView id,name,grade;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        view = findViewById(R.id.view);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        grade = findViewById(R.id.grade);
        delete = findViewById(R.id.delete);

         final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(name.getText().toString().equals("") || grade.getText().toString().equals("")){
                Toast.makeText(MainActivity.this,"Enter Data Completely",Toast.LENGTH_LONG).show();
            }
            else {
                boolean isinserted =databaseHelper.insert_data(name.getText().toString(),grade.getText().toString());
                if(isinserted){
                    Toast.makeText(MainActivity.this,"INSERTED",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"NOT INSERTED",Toast.LENGTH_LONG).show();
                }
            }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            boolean isupdated = databaseHelper.update_data(id.getText().toString(),name.getText().toString(),grade.getText().toString());
            if(isupdated){
                Toast.makeText(MainActivity.this, "Successfully updated",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this, "update unsuccessful",Toast.LENGTH_LONG).show();
            }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelper.getData();
                StringBuilder stringBuilder = new StringBuilder();
                if(cursor.getCount()!=0) {
                    while (cursor.moveToNext()) {
                        stringBuilder.append(cursor.getString(0) + " ");
                        stringBuilder.append(cursor.getString(1) + " ");
                        stringBuilder.append(cursor.getString(2) + "\n");

                    }
                    ShowMessage(stringBuilder.toString());
                }
                else {
                    ShowMessage("No data Found");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.delete(id.getText().toString());
            }
        });


    }

    public void ShowMessage(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(s);
        builder.show();
    }
}
