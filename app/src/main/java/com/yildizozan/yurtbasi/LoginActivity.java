package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

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
 * Created by Ozan on 8/5/2016.
 */

public class LoginActivity extends Activity {

    private TextView textViewWelcome;
    private TextView editTextPhoneNumber;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check login
        Database db = new Database(getApplicationContext());
        if (0 < db.getRowCount()) {
            new LoginCheck().execute(db.getMember());
        }

        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        // Toast message toast
        Toast.makeText(getApplicationContext(), "Yurtbaşı Köyü'ne hoşgeldiniz", Toast.LENGTH_SHORT).show();

        // DB entity control
        textViewWelcome.setText(db.getMember().getPhoneNumber() + "\n" +
                db.getMember().getName() + "\n" +
                db.getMember().getSurname() + "\n" +
                db.getMember().getRegister() + "\n" +
                db.getMember().getPassword() + "\n" +
                db.getMember().getPosition() + "\nDB: " +
                db.getRowCount()
        );

        db.close();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Login().execute(editTextPhoneNumber.getText().toString());
            }
        });


    }

    /*
    *** Login
     */
    public class Login extends AsyncTask<String, Integer, Boolean> {
        String connectionURL = "http://yildizozan.com/apis/village/loginProcess.php";

        private static final int TIMEOUT_READ = 3000;
        private static final int TIMEOUT_CONNECTION = 5000;

        private ProgressDialog mProgressDialog;

        private HttpURLConnection urlConnection;

        private Member mMember;

        private String mJSONString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Giriş yapılıyor...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
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
                    InputStream inputStream = new BufferedInputStream((urlConnection.getInputStream()));
                    if (inputStream != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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

            mJSONString = mJSONString.substring(4);
            Log.e("jsonChedckString", mJSONString); // CONTROL
            return true;
        }

        @Override
        protected void onPostExecute(Boolean loginCode) {
            super.onPostExecute(loginCode);

            if (loginCode == false) {
                mProgressDialog.dismiss();
                return;
            }

            try {
                JSONObject jsonRootObject = new JSONObject(mJSONString);
                JSONObject jsonObject = jsonRootObject.getJSONObject("user");

                // User is not found with the credentials
                if (jsonObject.getBoolean("error") == true) {
                    mProgressDialog.dismiss();
                    return;
                }

                mMember = new Member(jsonObject);

            } catch (Exception e) {
                Log.e("ERR CODE 834", e.getMessage());
            }

            Database db = new Database(getApplicationContext());
            if (0 < db.getRowCount())
                db.resetTables();

            if (0 == db.getRowCount()) {
                db.addMember(mMember);
                Intent intent = new Intent(LoginActivity.this, PasswordVerifyActivity.class);
                startActivity(intent);
            }

            db.close();

            // Progress dialog ending
            mProgressDialog.dismiss();
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer currentProgress = values[0];
            mProgressDialog.setProgressStyle(currentProgress);
        }
    }

    /*
    *** Login check
     */
    public class LoginCheck extends AsyncTask<Member, Boolean, Boolean> {

        String connectionURL = "http://yildizozan.com/apis/village/loginProcess.php";
        private HttpURLConnection urlConnection;

        private static final int TIMEOUT_READ = 3000;
        private static final int TIMEOUT_CONNECTION = 5000;

        private String mJSONString;

        private Member mMember;

        @Override
        protected Boolean doInBackground(Member... params) {

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
                        URLEncoder.encode(params[0].getPhoneNumber(), "UTF-8");

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
                    InputStream inputStream = new BufferedInputStream((urlConnection.getInputStream()));
                    if (inputStream != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null)
                            mJSONString += line;
                    }
                }

            } catch (Exception e) {
                Log.e("ERR CODE 445", e.getMessage());
                return false;
            } finally {
                urlConnection.disconnect();
            }

            mJSONString = mJSONString.substring(4);
            try {
                JSONObject jsonRootObject = new JSONObject(mJSONString);
                JSONObject jsonObject = jsonRootObject.getJSONObject("user");

                // User is not found with the credentials
                if (jsonObject.getBoolean("error") == true) {
                    return false;
                }

                mMember = new Member(jsonObject);

            } catch (Exception e) {
                Log.e("ERR CODE 291", e.getMessage());
            }

            // json and db check
            if (mMember.getPhoneNumber() == params[0].getPhoneNumber() && mMember.getPassword() == params[0].getPassword()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            return null;
        }
    }
}