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

    private EditText editTextPassword;

    private Member mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordverify);

        // Content oluştuktan sonra gelen intent datasını hemen alıyoruz.
        mMember = (Member) getIntent().getSerializableExtra("memberforpasswordverify");

        // Ekrandaki componentleri tanımlıyoruz
        final TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        final Button buttonVerify = (Button) findViewById(R.id.buttonVerify);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    // Butona tıklandığında girilen şifreyi kontrol edecek.
    public void PasswordVerify(View v) {
        if (editTextPassword.getText().toString().isEmpty())
            editTextPassword.setError("Lütfen şifre yazınız.");
        else if (mMember.getPassword() == Integer.parseInt(editTextPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Tekrar hoşgeldiniz.", Toast.LENGTH_SHORT).show();

            // Main activity için giriş yapılıyor.
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        } else
            editTextPassword.setError("Şifre yanlış.");
    }
}