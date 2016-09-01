package com.yildizozan.melikserif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class PasswordVerifyActivity extends Activity {

    private Database db;
    private EditText editTextPassword;

    private Member member;

    public PasswordVerifyActivity() {
        super();
        db = new Database(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (db.getRowCount() == 1) {
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (db.getRowCount() == 1) {
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_passwordverify);

        // Content oluştuktan sonra gelen intent datasını hemen alıyoruz.
        member = (Member) getIntent().getSerializableExtra("memberforpasswordverify");

        // Ekrandaki componentleri tanımlıyoruz
        final TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        final Button buttonVerify = (Button) findViewById(R.id.buttonVerify);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db.getRowCount() == 1) {
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    // Butona tıklandığında girilen şifreyi kontrol edecek.
    public void PasswordVerify(View v) {
        // Şifre doğru mu diye kontrol ediyoruz
        if (editTextPassword.getText().toString().equals(member.getPassword())) {
            Toast.makeText(
                    getApplicationContext(),
                    member.getFirstName() + " " + member.getFamilyName() + " tekrar hoşgeldiniz.",
                    Toast.LENGTH_LONG).show();

            // Kişiyi database'e kayıt ediyoruz.
            Database db = new Database(this);
            Log.d("Insert: ", "Inserting ..");
            db.addMember(member);

            // Main activity için giriş yapılıyor.
            Intent intent = new Intent(PasswordVerifyActivity.this, MainActivity.class);
            startActivity(intent);
        }
        // Boş şifre durumunu değerlendiriyoruz
        else if (editTextPassword.getText().toString().isEmpty())
            editTextPassword.setError("Lütfen şifre yazınız.");
        else
            editTextPassword.setError("Şifre yanlış.");
    }
}