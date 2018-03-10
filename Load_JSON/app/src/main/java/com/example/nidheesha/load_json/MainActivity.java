package com.example.nidheesha.load_json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockApplication;
import android.view.ViewDebug;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    Recycler_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        new getJSON().execute();
    }
    private class getJSON extends AsyncTask<String,Integer,String>{
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection connection;
        URL url;

        public getJSON() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            List<Contact> data = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jobj = (JSONObject) jsonArray.get(i);
                    Contact contact = new Contact();
                    contact.id = jobj.getString("id");
                    contact.name = jobj.getString("name");
                    contact.address = jobj.getString("address");
                    contact.email = jobj.getString("email");
                    contact.gender=jobj.getString("gender");
                    data.add(contact);

                }
                adapter = new Recycler_adapter(MainActivity.this,data);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setAdapter(adapter);
                rv.setLayoutManager(linearLayoutManager);
            }catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                url = new URL("https://api.androidhive.info/contacts/");
            }catch (MalformedURLException e){
                e.printStackTrace();
                return e.toString();
            }
            try{
                connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(19000);
                connection.setConnectTimeout(19000);
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
            }catch (IOException e){
                e.printStackTrace();
                return e.toString();
            }
            try{
                int request_Code = connection.getResponseCode();
                if(request_Code == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while( (line = bufferedReader.readLine())!= null){
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                }
                else {
                    return "unsuccessful";
                }
            }catch (IOException e){
                    e.printStackTrace();
                    return e.toString();
            }finally {
                connection.disconnect();
            }

        }
    }
}
