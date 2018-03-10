package com.example.nidheesha.recview_async_json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    Recycler_adapter adapter;
//    List<String>data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        data = new ArrayList<String>();
        new GetJson().execute();

    }


private class GetJson extends AsyncTask<String,Integer,String>{
    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
    HttpURLConnection connection;
    URL url = null;
//    List<String> data = new ArrayList<>();

//    public GetJson() {
//        super();
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        List<Item> datas = new ArrayList<>();

        try {
            JSONObject job = new JSONObject(s);
            JSONArray jarray = job.getJSONArray("item");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject json_data = jarray.getJSONObject(i);
                Item item = new Item();
                item.data = json_data.getString("data");

                Log.d("TAG", "onPostExecute: "+item.data );
                datas.add(item);
               // Log.d("TAG", "onPostExecute: "+ data.toString());
            }
            rv = (RecyclerView) findViewById(R.id.rv);
            adapter = new Recycler_adapter(MainActivity.this, datas);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setAdapter(adapter);
            rv.setLayoutManager(linearLayoutManager);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        try{        url = new URL("http://www.json-generator.com/api/json/get/cvatZDaRea?indent=2");
        }
        catch (MalformedURLException e){
            e.printStackTrace();
                    return e.toString();
        }
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(19000);
            connection.setConnectTimeout(19000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
        }
        catch (IOException e){
            e.printStackTrace();
            return e.toString();
        }
        try{
        int response_code = connection.getResponseCode();
        if(response_code == HttpURLConnection.HTTP_OK){
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                result.append(line);
            }
//            connection.disconnect();
            return result.toString();
        }
        else {
//            connection.disconnect();
        return ("unsuccessful");
    }}
    catch (IOException e){
            e.printStackTrace();
            return  e.toString();
    }finally {
        connection.disconnect();
        }
        }
}
}