package com.yildizozan.melikserif;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class MainActivity extends Activity {

    private Database db;

    private List<News> newses = new ArrayList<>();

    ListView listViewForNews;

    public MainActivity() {
        super();

        db = new Database(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Webden dataları çekiyoruz ve ArrayList'imize atıyoruz.
        new getAllNews().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    /*
        ***
         */
    private class getAllNews extends AsyncTask<String, Boolean, Boolean> {

        // Timeouts
        private static final int TIMEOUT_READ = 7 * 1000;           // milisec
        private static final int TIMEOUT_CONNECTION = 10 * 1000;    // milisec

        private final String connectionURL = "http://project.yildizozan.com/melikserif/api/getAllNews.php";

        private HttpURLConnection urlConnection;
        private String jsonString;

        private ProgressDialog progressDialog;

        public getAllNews() {
            super();
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Haberler yükleniyor");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // Start connection
            try {
                URL url = new URL(connectionURL);
                urlConnection = (HttpURLConnection) url.openConnection();

                // Setting for timeouts
                urlConnection.setReadTimeout(TIMEOUT_READ);
                urlConnection.setConnectTimeout(TIMEOUT_CONNECTION);

                // Setting for input output
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                // Prepare HTTP Header
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


                // Prepare output streaming
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8")
                );

                // Connect!
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == urlConnection.HTTP_OK)
                {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    if (inputStream != null)
                    {
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(inputStream, "UTF-8")
                        );
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            jsonString += line;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("CTRL 377", e.getMessage());
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("CTRL 378", e.getMessage());
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("CTRL 379", e.getMessage());
                return false;
            } finally {
                urlConnection.disconnect();
            }

            Log.i("CTRL 9653", jsonString);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            // ProgressDialog'tan kurtuluyoruz
            progressDialog.dismiss();

            // Anasayfamızda sadace haberler gösterileceği için LinearLayout şeklinde sıralayacağız.
            listViewForNews= (ListView) findViewById(R.id.ListViewNews);

            if (aBoolean) {
                JSONParser jsonParser = new JSONParser(jsonString);
                if (jsonParser.setNews()) {
                    newses = jsonParser.getNews();

                    // Adaptörümüzü oluşturuyoruz daha sonra ilgili habere tıklandığında haber sayfası açılacak.
                    NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newses);
                    listViewForNews.setAdapter(newsAdapter);
                    listViewForNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, NewsShowActivity.class);
                            News news = newses.get(position);
                            intent.putExtra("NEWSINFO", news);
                            startActivity(intent);
                        }
                    });
                }
            } else {
                Toast.makeText(MainActivity.this, "Haberlere ulaşılamadı!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}