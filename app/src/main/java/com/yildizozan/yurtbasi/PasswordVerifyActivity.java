package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class PasswordVerifyActivity extends Activity {

    private TextView textViewResult;
    private EditText editTextPassword;
    private Button buttonVerify;

    private Member mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordverify);

        textViewResult = (TextView) findViewById(R.id.textViewResult);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonVerify = (Button) findViewById(R.id.buttonVerify);

        // Gelen intent datasını alıyoruz.
        mMember = (Member) getIntent().getSerializableExtra("memberforpasswordverify");


    }

    // Butona tıklandığında girilen şifreyi kontrol edecek.
    public void PasswordVerify(View v) {
        if (editTextPassword.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Lütfen şifre yazınız.", Toast.LENGTH_SHORT).show();
        else if (mMember.getPassword() == Integer.parseInt(editTextPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Şifre doğru.", Toast.LENGTH_SHORT).show();

            // Main activity için giriş yapılıyor.
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplication(), "Şifre yanlış.", Toast.LENGTH_SHORT).show();
        }
    }

}