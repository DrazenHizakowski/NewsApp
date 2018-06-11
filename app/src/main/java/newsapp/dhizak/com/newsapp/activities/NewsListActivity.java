package newsapp.dhizak.com.newsapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.activities.adapters.NewsListNewsAdapter;
import newsapp.dhizak.com.newsapp.activities.custom_views.EndlessRecyclerViewScrollListener;
import newsapp.dhizak.com.newsapp.activities.custom_views.SearchWidget;
import newsapp.dhizak.com.newsapp.activities.presenters.NewsListPresenter;
import newsapp.dhizak.com.newsapp.activities.presenters.NewsListPresenterImpl;
import newsapp.dhizak.com.newsapp.activities.views.NewsListView;
import newsapp.dhizak.com.newsapp.model.Article;
import newsapp.dhizak.com.newsapp.networking.NewsAppInterface;

public class NewsListActivity extends AppCompatActivity implements NewsListView {
    private static final String TAG = "NewsListActivity";

    @BindView(R.id.news_list_search_edit_text)
    EditText searchField;
    @BindView(R.id.news_list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.news_list_progress_layout)
    FrameLayout progressLayout;
    @BindView(R.id.news_list_message)
    TextView messageText;
    @BindView(R.id.news_list_search_icon)
    SearchWidget searchButton;

    private NewsListPresenter newsListPresenter;
    private NewsListNewsAdapter newsListNewsAdapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        this.init();
        this.newsListPresenter = new NewsListPresenterImpl(this);
        this.newsListPresenter.init(savedInstanceState);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        this.messageText.setText(message);
    }

    @Override
    public void addQueryResults(List<Article> articles) {
        newsListNewsAdapter.addSearchQueryResults(articles);
    }

    @Override
    public void setQueryResults(List<Article> articles) {
        endlessScrollListener.resetState();
        newsListNewsAdapter.setSearchQueryResults(articles);
    }

    @Override
    public void showQueryEmptyError() {
        Toast.makeText(this,R.string.query_empty,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(boolean show) {
        progressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setQueryText(String text) {
        searchField.setText(text);
    }

    @Override
    public void setTopHeadlinesAndLatestNews(List<Article> topHeadlines, List<Article> latestNews) {
        endlessScrollListener.resetState();
        newsListNewsAdapter.addTopHeadlinesAndNewest(topHeadlines, latestNews);
    }

    @Override
    public void changeButtonType(int type) {
        searchButton.setType(type);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        newsListPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        newsListPresenter.onPause();
        super.onPause();
    }

    /**
     * Initiates needed objects for layout
     */
    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsListNewsAdapter = new NewsListNewsAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsListNewsAdapter);
        initOnClickListeners();
        setEndlessScrollListener(layoutManager);
    }

    private void initOnClickListeners() {
        searchButton.setListener(new SearchWidget.OnSearchWidgetClickListener() {
            @Override
            public void onClearButtonClicked() {
                newsListPresenter.onClearButtonClicked();
            }

            @Override
            public void onSearchButtonClicked() {
                newsListPresenter.onQueryChanged(searchField.getText().toString());
            }
        });
        searchField.setOnEditorActionListener((textView, action, keyEvent) -> {
            if(action == EditorInfo.IME_ACTION_DONE){
                newsListPresenter.onQueryChanged(searchField.getText().toString());
            }
            return false;
        });
    }

    private void setEndlessScrollListener(LinearLayoutManager layoutManager){
        endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page = ((totalItemsCount-1) / NewsAppInterface.pageSize)+1; //-1 because of headers, could be better, +1 for new page
                newsListPresenter.onLoadMoreSearchResults(searchField.getText().toString(),page,totalItemsCount);
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
    }

}
