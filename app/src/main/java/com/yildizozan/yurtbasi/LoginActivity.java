package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class LoginActivity extends Activity {

    private TextView editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                else
                    new Connection(LoginActivity.this).execute(editTextPhoneNumber.getText().toString());
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
}