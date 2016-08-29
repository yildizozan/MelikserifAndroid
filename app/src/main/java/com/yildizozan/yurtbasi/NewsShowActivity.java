package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Ozan on 8/28/2016.
 */

public class NewsShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsshow);

        // İlk işimiz intent ile gelen datayı almak olacak
        News news = (News) getIntent().getSerializableExtra("NEWSINFO");
    }
}
