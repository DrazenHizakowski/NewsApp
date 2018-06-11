package newsapp.dhizak.com.newsapp.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.activities.holders.NewsListHeadlineItemHolder;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class NewsListHeadlinesAdapter extends RecyclerView.Adapter<NewsListHeadlineItemHolder> {
    private static String TAG = "NewsListHeadlinesAdapter";

    private List<Article> articles = new ArrayList<>();

    @NonNull
    @Override
    public NewsListHeadlineItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_list_top_headlines_item,parent,false);
        return new NewsListHeadlineItemHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHeadlineItemHolder holder, int position) {
        holder.bindData(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addItems(List<Article> articles){
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }
}
