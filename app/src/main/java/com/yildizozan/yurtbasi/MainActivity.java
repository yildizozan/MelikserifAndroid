package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class MainActivity extends Activity {

    final List<News> newses = new ArrayList<News>();

    // Example datas
    final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eget purus nec ligula luctus semper. Sed eu augue ut metus gravida dapibus quis quis libero. Nam ac posuere nisl. Aenean gravida dignissim sapien non laoreet. Curabitur vitae faucibus nulla. Morbi lacinia quis enim vitae vestibulum. Nunc ut aliquam est, ut facilisis metus. Phasellus efficitur, augue eget consequat semper, augue enim sodales metus, sed mattis eros enim et sapien. Integer faucibus consectetur nisl et cursus. Quisque placerat purus lorem, eu tincidunt dui maximus ut. Suspendisse potenti. Duis ac fringilla dolor, quis porta enim.\n" +
            "\n" +
            "Aenean condimentum ante nisi. Donec eget turpis nulla. Aliquam vitae interdum erat. In et luctus tortor, quis rutrum risus. Mauris risus dui, mattis in tincidunt sit amet, mollis non enim. Mauris finibus, dui sed faucibus venenatis, est sapien consectetur magna, semper sollicitudin enim dolor a nulla. Morbi maximus vehicula magna blandit porttitor. Proin pretium cursus elit, ac condimentum nisi varius vitae. Vivamus maximus, neque id bibendum sollicitudin, lacus ligula suscipit libero, id sagittis mauris ligula vitae lorem. Vestibulum posuere semper pulvinar. Etiam condimentum urna odio, in venenatis tellus maximus ac. Vivamus mattis, tortor ac sagittis pellentesque, metus augue imperdiet enim, sit amet faucibus ante diam ut leo. Vestibulum id nisl neque.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Anasayfamızda sadace haberler gösterileceği için LinearLayout şeklinde sıralayacağız.
        ListView listViewForNews = (ListView) findViewById(R.id.ListViewNews);

        // Haberler listemizi hazırlıyoruz
        newses.add(new News("16-08-2016", "Konya gezisi", loremIpsum, "Ozan Yıldız"));
        newses.add(new News("16-08-2016", "Ankara gezisi", loremIpsum, "Nazım"));
        newses.add(new News("16-08-2016", "İstanbul gezisi", loremIpsum, "Yusuf"));

        // Adaptörümüzü oluşturuyoruz daha sonra ilgili habere tıklandığında haber sayfası açılacak.
        NewsAdapter newsAdapter = new NewsAdapter(this, newses);
        listViewForNews.setAdapter(newsAdapter);
        listViewForNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, NewsShowActivity.class);
                    News news = newses.get(position);
                    intent.putExtra("NEWSINFO", news);
                    startActivity(intent);
            }
        });

    }
}