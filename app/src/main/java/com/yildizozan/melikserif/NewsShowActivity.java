package com.yildizozan.melikserif;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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

        TextView textViewNewsTitle = (TextView) findViewById(R.id.textViewNewsTitle);
        TextView textViewNewsContent = (TextView) findViewById(R.id.textViewNewsContent);
        TextView textViewNewsAuthor = (TextView) findViewById(R.id.textViewNewsAuthor);
        TextView textViewNewsDate = (TextView) findViewById(R.id.textViewNewsDate);

        textViewNewsTitle.setText(news.getTitle());
        textViewNewsContent.setText(news.getContent());
        textViewNewsAuthor.setText(news.getAuthor());
        textViewNewsDate.setText(news.getDate());
    }
}
