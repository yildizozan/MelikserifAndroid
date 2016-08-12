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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPhoneNumber.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Numara yazınız.", Toast.LENGTH_SHORT).show();
                else if (editTextPhoneNumber.length() != 10)
                    Toast.makeText(getApplicationContext(), "Başında sıfır olmadan 10 haneli numaranızı yazınız.", Toast.LENGTH_SHORT).show();
                else
                    new Connection(LoginActivity.this).execute(editTextPhoneNumber.getText().toString());
            }
        });
    }
}