package com.yildizozan.yurtbasi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class Connection extends AsyncTask<String, Boolean, Boolean> {
    // Api link
    String connectionURL = "http://yildizozan.com/apis/village/loginProcess.php";

    // Api key
    String connectionKey;

    // Timeouts
    private static final int TIMEOUT_READ = 3000;
    private static final int TIMEOUT_CONNECTION = 5000;

    private HttpURLConnection urlConnection;
    private URL url;

    private Context mContext;

    private String mJSONString;

    public Connection(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(mContext, "Bağlanılıyor...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            // Start connection
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

            // Prepare data for sending
            String data = URLEncoder.encode("phoneNumber", "UTF-8") + "=" +
                    URLEncoder.encode(params[0], "UTF-8");

            // Prepare output streaming
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8")
            );
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            // Connect!
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == urlConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream, "UTF-8")
                    );
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        mJSONString += line;
                }
            }

        } catch (Exception e) {
            Log.e("ERR CODE 238", e.getMessage());
            return false;
        } finally {
            urlConnection.disconnect();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        // Eğer bağlantı sırasında herhangi bir hata gelirse return döndürecek
        // Toast ile bilgi verecek.
        if (aBoolean == false) {
            Toast.makeText(mContext, "Bağlantı hatası.", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONParser jsonParser = new JSONParser(mJSONString);
        if (jsonParser.setMember())
            Toast.makeText(mContext,
                    jsonParser.getmMember().getPhoneNumber() + " " +
                    jsonParser.getmMember().getName() + " " +
                    jsonParser.getmMember().getSurname() + " " +
                    jsonParser.getmMember().getRegister(),
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mContext, mJSONString, Toast.LENGTH_SHORT).show();

    }
}