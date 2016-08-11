package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */
 
public class PasswordVerifyActivity extends Activity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordverify);

        textViewResult = (TextView) findViewById(R.id.textViewResult);

        // Gelen intent datasını alıyoruz.
        Member member = (Member) getIntent().getSerializableExtra("memberforpasswordverify");
        textViewResult.setText(member.getMemberResult());

    }
}
