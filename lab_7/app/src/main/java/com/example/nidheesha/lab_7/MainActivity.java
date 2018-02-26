package com.example.nidheesha.lab_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    TextView cont ,content_panel;
    ImageView imageView;
    private ActionMode mActionMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = findViewById(R.id.cont);
        content_panel =findViewById(R.id.contentPanel);
        imageView = findViewById(R.id.image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this,Settings.class);
                startActivity(i);
            }
        });

        setUpSharedPreferences();
        TextView textView = (TextView) findViewById(R.id.tv);

        // add long click listener to text view
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB (Context Action Bar) using the ActionMode.Callback defined above
                mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

            return false;

        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.yes:
                    Toast.makeText(getApplicationContext(), "Thanks for liking!", Toast.LENGTH_SHORT).show();
                    actionMode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.no:
                    Toast.makeText(getApplicationContext(), "We'll try to improve", Toast.LENGTH_SHORT).show();
                    actionMode.finish(); // Action picked, so close the CAB
                    return true;

                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };




    public void setUpSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setItalic(sharedPreferences.getBoolean("italiac",true));
        setMydisplay(sharedPreferences.getString("display","image"));
//        setMyTheme(sharedPreferences.getString("theme","nature"));
//        setMyName(sharedPreferences.getString("name","User"));

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void setItalic(boolean b) {
        SpannableString spannableString = new SpannableString(cont.getText().toString());

        if(b)
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC),0,spannableString.length(),0);
            //tvHello.setTypeface(null, Typeface.ITALIC);
        else
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL),0,spannableString.length(),0);
        //tvHello.setTypeface(null,Typeface.NORMAL);
        cont.setText(spannableString);
    }

    public void setMydisplay(String val){
        if(val.equals("image")){
            content_panel.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        if (val.equals("content")){
            imageView.setVisibility(View.GONE);
            content_panel.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("italiac"))
        {
            setItalic(sharedPreferences.getBoolean("italiac",true));
        }
        if(s.equals("display")){
            setMydisplay(sharedPreferences.getString("display","image"));
        }
//        if(s.equals("name")){
//            setMyName(sharedPreferences.getString("name","User"));
//        }
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
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id)
        {
            case R.id.about:
                openabout();
                return true;

            case R.id.popupmenu:
                popupmenu_implementation(id);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }

    public void openabout(){
        Intent i = new Intent(MainActivity.this,about.class);
        startActivity(i);
    }

    public  void popupmenu_implementation(int id){
        PopupMenu popupMenu = new PopupMenu(this,findViewById(id));
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getTitle().toString().equals("Pug")){
                    imageView.setImageResource(R.drawable.pug);
                    content_panel.setText("PUG");
                }
                else if (item.getTitle().toString().equals("Poodle")){
                    imageView.setImageResource(R.drawable.poodle);
                    content_panel.setText("POODLE");
                }
                Toast.makeText(MainActivity.this,"Chose"+item.getTitle(),Toast.LENGTH_LONG).show();
                return true;
            }
        });
        popupMenu.show();
    }

}
