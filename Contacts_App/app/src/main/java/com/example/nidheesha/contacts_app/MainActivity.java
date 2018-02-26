package com.example.nidheesha.contacts_app;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    TextView t;
    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView)findViewById(R.id.text);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        getSupportLoaderManager().initLoader(0,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTACTS_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(this,CONTACTS_URI,null,null,null,null);
//        new CursorLoader(this,CONTACTS_URI,null,null,null)
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader,final Cursor  data) {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.moveToFirst();
                load('a','e',data);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.moveToFirst();
                load('f','k',data);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.moveToFirst();
                load('l','z',data);
            }
        });

    }
    public void load(char first,char last,Cursor data){
        StringBuilder sb =new StringBuilder();
        if(data.getCount()>0){
            while(data.moveToNext()){
                    String id = data.getString(data.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if(first <= name.charAt(0)&& name.charAt(0)<=last || (first-32) <= name.charAt(0)&& name.charAt(0)<= (last-32)){
                    int hasPhonenumber  = Integer.parseInt(data.getString(data.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if(hasPhonenumber>0){
                        Cursor c = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{String.valueOf(id)},
                                null);
                        while(c.moveToNext()){
                            String pn = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            sb.append("contact: "+name+"\n"+"Contact: "+pn+"\n\n");
                        }
                            c.close();
                    }
                }

            }
        }

        t.setText(sb.toString());
//        data.close();
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
