package com.yildizozan.yurtbasi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ozan on 8/28/2016.
 */

public class NewsAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<News> mNewses;

    public NewsAdapter(Activity pActivity, List<News> pNewses) {
        mLayoutInflater = (LayoutInflater) pActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNewses = pNewses;
    }

    @Override
    public int getCount() {
        return mNewses.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view = mLayoutInflater.inflate(R.layout.news_row, null);

        TextView textViewNewsDate = (TextView) view.findViewById(R.id.textViewNewsDate);
        TextView textViewNewsTitle = (TextView) view.findViewById(R.id.textViewNewsTitle);
        TextView textViewNewsContent = (TextView) view.findViewById(R.id.textViewNewsContent);
        TextView textViewNewsAuthor = (TextView) view.findViewById(R.id.textViewNewsAuthor);

        News news = mNewses.get(position);

        textViewNewsDate.setText(news.getmDate());
        textViewNewsTitle.setText(news.getmTitle());
        textViewNewsContent.setText(news.getmContent());
        textViewNewsAuthor.setText("Yazar: " + news.getmAuthor());

        return view;
    }
}
