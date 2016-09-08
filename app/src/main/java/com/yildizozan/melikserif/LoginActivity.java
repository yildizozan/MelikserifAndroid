package com.yildizozan.melikserif;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.net.URLEncoder;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class LoginActivity extends Activity {

    private TextView editTextPhoneNumber;
    private Database db;

    public LoginActivity() {
        super();
        db = new Database(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (db.getRowCount() == 1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (db.getRowCount() == 1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);

        final ImageView imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
        final Button loginButton = (Button) findViewById(R.id.buttonLogin);
        final Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

        Animation startTotateAnimsttion = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        startTotateAnimsttion.start();
        imageViewLogo.startAnimation(startTotateAnimsttion);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPhoneNumber.getText().toString().isEmpty())
                    editTextPhoneNumber.setError("Numara yazınız.");
                else if (editTextPhoneNumber.length() != 10)
                    editTextPhoneNumber.setError("Başında sıfır olmadan numaranızı yazınız.");
                else    // Form doldurulmuşsa activity çalışıyor.
                    new Login(LoginActivity.this).execute();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db.getRowCount() == 1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    /*
        ***
         */
    private class Login extends AsyncTask<String, Boolean, Boolean> {

        // Timeouts
        private static final int TIMEOUT_READ = 7 * 1000;           // milisec
        private static final int TIMEOUT_CONNECTION = 10 * 1000;    // milisec

        // Api link
        private final String connectionURL = "http://project.yildizozan.com/melikserif/api/login.php";

        // Api key
        private String connectionKey;

        // Progress dialog
        private ProgressDialog mProgressDialog;

        private String phoneNumber;
        private HttpURLConnection urlConnection;
        private Context mContext;
        private String mJSONString;

        Login(Context context) {
            this.mContext = context;
            mProgressDialog = new ProgressDialog(context);
        }

        /*
        Dostumuz burada gelen content bilgisiyle birlikte
        Progress dialog oluşturacak
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Giriş yapılıyor...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            phoneNumber = editTextPhoneNumber.getText().toString();

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
                        URLEncoder.encode(phoneNumber, "UTF-8");

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

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("ERR CONN 377", e.getMessage());
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("ERR CONN 378", e.getMessage());
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ERR CONN 379", e.getMessage());
                return false;
            } finally {
                urlConnection.disconnect();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            // Progress dialog finish for toast message
            mProgressDialog.dismiss();

            // Eğer bağlantı sırasında herhangi bir hata gelirse return döndürecek.
            // Toast ile bilgi verecek.
            if (!aBoolean) {
                Toast.makeText(mContext, "Bağlantı hatası.", Toast.LENGTH_SHORT).show();
                Log.e("ERR CONN 349", "aBoolean false");
                return;
            }

            JSONParser jsonParser = new JSONParser(mJSONString);
            if (jsonParser.setMember()) {
                Toast.makeText(mContext, "Şifre: " + jsonParser.getMember().getPassword(), Toast.LENGTH_SHORT).show();

                // If there is member in the database.
                Intent intent = new Intent(mContext, PasswordVerifyActivity.class);
                Member member = jsonParser.getMember();
                intent.putExtra("memberforpasswordverify", member);
                mContext.startActivity(intent);
            }
            else {
                Toast.makeText(mContext, "Kişi bulunamadı.", Toast.LENGTH_SHORT).show();
                Log.e("ERR CONN 350", mJSONString);
            }
        }
    }
}