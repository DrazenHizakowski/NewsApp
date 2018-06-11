package newsapp.dhizak.com.newsapp.activities.holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.activities.adapters.NewsListHeadlinesAdapter;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 10/06/2018.
 */

public class NewsListHeadlineListHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.headlines_recycler_view) RecyclerView recyclerView;

    private List<Article> articles;

    public NewsListHeadlineListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindData(List<Article> articles){
        this.articles = articles;
        init();
    }

    /**
     * Initiates Top Headlines list adapter {@link newsapp.dhizak.com.newsapp.activities.adapters.NewsListHeadlinesAdapter}
     */
    private void init() {
        NewsListHeadlinesAdapter adapter = new NewsListHeadlinesAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter.addItems(articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
