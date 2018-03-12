package com.example.ghost.newsapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String url;
    ListView listNews;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=6fcf9cfe37024ac4b3ccdd0187c91568";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listNews = (ListView) findViewById(R.id.listNews);
        loader = (ProgressBar) findViewById(R.id.loader);
        listNews.setEmptyView(loader);
        new MainActivity.Asynctask().execute(url);
    }

    public class Asynctask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR));
                    map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE));
                    map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION));
                    map.put(KEY_URL, jsonObject.optString(KEY_URL));
                    map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE));
                    map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT));
                    dataList.add(map);
                }
            } catch (JSONException e) {

            }

            ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this, dataList);
            listNews.setAdapter(adapter);

            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                    i.putExtra("url", dataList.get(+position).get(KEY_URL));
                    startActivity(i);
                }
            });

        }

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlconnection = null;

            try {
                url = new URL(urls[0]);
                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestProperty("content-type", "application/json;  charset=utf-8");


                urlconnection.setRequestProperty("Content-Language", "en-US");

                urlconnection.setUseCaches(false);
                urlconnection.setDoInput(true);
                urlconnection.setDoOutput(false);

                String response = streamtostring(urlconnection.getInputStream());

                return response;


            } catch (Exception e) {

            } finally {

                if (urlconnection != null) {
                    urlconnection.disconnect();
                }
            }
            return null;

        }
    }


    String streamtostring(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        StringBuffer response = new StringBuffer();
        while ((data = bufferedReader.readLine()) != null) {
            response.append(data);
            response.append('\r');

        }
        if (stream != null) {
            stream.close();
        }
        return response.toString();

    }


}
