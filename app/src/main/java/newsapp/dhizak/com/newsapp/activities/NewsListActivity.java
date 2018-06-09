package newsapp.dhizak.com.newsapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.activities.views.NewsListView;

public class NewsListActivity extends AppCompatActivity implements NewsListView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

}
