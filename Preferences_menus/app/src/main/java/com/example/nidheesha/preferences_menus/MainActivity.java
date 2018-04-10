package com.example.nidheesha.preferences_menus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,Settings.class));

            }
        });
        setupSharedPreference();

        TextView t = findViewById(R.id.greet);
        t.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ActionMode actionMode = MainActivity.this.startActionMode(actionmode);

                return true;
            }
        });
    }

    public ActionMode.Callback actionmode = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.context_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.yes:
                    Toast.makeText(MainActivity.this,"yes",Toast.LENGTH_LONG).show();
                    actionMode.finish();
                    return true;
                case R.id.no:
                    Toast.makeText(MainActivity.this,"no",Toast.LENGTH_LONG).show();
                    actionMode.finish();
                    return  true;
                default:
                    return false;

            }
//            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };

    public void setupSharedPreference(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setmyText(sharedPreferences.getBoolean("check_box_preference",false));
        setmyText2(sharedPreferences.getString("edit_text_preference","USER"));
        setimage(sharedPreferences.getString("List_preference","image_show"));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    public void setmyText(Boolean b){
        TextView t = findViewById(R.id.greet);
        SpannableString spannableString = new SpannableString(t.getText().toString());
        if(b){
            Log.d("TAG", "setmyText: . "+spannableString.toString());
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC),0,spannableString.length(),0);
//            t.setText(spannableString.toString());
        }
        else {
            Log.d("TAG", "setmyText: to normal................................. ");

            spannableString.setSpan(new StyleSpan(Typeface.NORMAL),0,spannableString.length(),0);
        }
        t.setText(spannableString);
    }
    public void setmyText2(String s){
        TextView t = findViewById(R.id.greet);
        t.setText("HELLO "+s+"!");

    }
    public void setimage(String s){
        ImageView imageView = findViewById(R.id.image);
        if(s.equals("image_show")){
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("check_box_preference")){
            setmyText(sharedPreferences.getBoolean("check_box_preference",false));
        }
        else if(s.equals("edit_text_preference")){
            setmyText2(sharedPreferences.getString("edit_text_preference","USER"));
        }
        else if(s.equals("List_preference")){
            setimage(sharedPreferences.getString("List_preference","image_show"));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            popup(id);
        }
        else if(id == R.id.search){

        }

        return super.onOptionsItemSelected(item);
    }


    public void popup(int id){
        PopupMenu popupMenu = new PopupMenu(this,findViewById(id));
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().toString().equals("item 1")){
                    Toast.makeText(MainActivity.this,"item1",Toast.LENGTH_LONG).show();
                }
                else if(item.getTitle().toString().equals("item 2")){
                    Toast.makeText(MainActivity.this,"item2",Toast.LENGTH_LONG).show();
                }
                else if(item.getTitle().toString().equals("item 3")){
                    Toast.makeText(MainActivity.this,"item3",Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });
        popupMenu.show();
    }


}
