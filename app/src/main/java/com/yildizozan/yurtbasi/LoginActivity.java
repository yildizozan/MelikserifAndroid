package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class LoginActivity extends Activity {

    private TextView textViewWelcome;
    private TextView editTextPhoneNumber;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        // Toast message toast
        Toast.makeText(getApplicationContext(), "Yurtbaşı Köyü'ne hoşgeldiniz.", Toast.LENGTH_SHORT).show();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Connection(LoginActivity.this).execute(editTextPhoneNumber.getText().toString());
            }
        });
    }
}