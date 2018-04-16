package com.example.nidheesha.connect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText number;
    Button send_sms,call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText)findViewById(R.id.number);
        send_sms = (Button)findViewById(R.id.send_sms);
        call = (Button)findViewById(R.id.call);
        send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendsms();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makecall();
            }
        });
    }
    public void makecall(){
        Intent calling  =  new Intent(Intent.ACTION_CALL);
        calling.setData(Uri.parse("tel:"+number.getText().toString()));
        if(checkSelfPermission(android.Manifest.permission.CALL_PHONE)==getPackageManager().PERMISSION_GRANTED)
        startActivity(calling);
    }



    public void sendsms(){
        Uri uri = Uri.parse("smsto:"+ number.getText().toString());
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO,uri);
        sendIntent.putExtra("sms_body","xxxxxxxx");
        String Title = "choose";
        Intent chooser = Intent.createChooser(sendIntent,Title);
        if(sendIntent.resolveActivity(getPackageManager())!=null){
            startActivity(chooser);
        }
    }
}
