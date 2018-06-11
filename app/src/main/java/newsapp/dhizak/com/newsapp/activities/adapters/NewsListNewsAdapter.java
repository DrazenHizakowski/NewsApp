package newsapp.dhizak.com.newsapp.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.activities.holders.NewsListArticleHolder;
import newsapp.dhizak.com.newsapp.activities.holders.NewsListHeaderHolder;
import newsapp.dhizak.com.newsapp.activities.holders.NewsListHeadlineListHolder;
import newsapp.dhizak.com.newsapp.activities.presenters.NewsListPresenter;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 06/06/2018.
 */

public class NewsListNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = "NewsListNewsAdapter";

    private static final int VIEW_TYPE_HEADER_TOP_HEADLINES = 0;
    private static final int VIEW_TYPE_HEADER_NEWEST_ARTICLES = 1;
    private static final int VIEW_TYPE_HEADER_SEARCH_ARTICLES = 2;
    private static final int VIEW_TYPE_TOP_HEADLINES = 3;
    private static final int VIEW_TYPE_NEWEST_ARTICLES = 4;
    private static final int VIEW_TYPE_SEARCH_RESULTS = 5;

    private List<Article> headlinesList = new ArrayList<>();
    private List<Article> newestArticlesList = new ArrayList<>();
    private List<Article> queryResultsList = new ArrayList<>();

    private int currentType = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER_NEWEST_ARTICLES ||
                viewType == VIEW_TYPE_HEADER_SEARCH_ARTICLES ||
                viewType == VIEW_TYPE_HEADER_TOP_HEADLINES){
            return new NewsListHeaderHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_news_list_header,parent,false));
        }else if(viewType == VIEW_TYPE_SEARCH_RESULTS || viewType == VIEW_TYPE_NEWEST_ARTICLES){
            return new NewsListArticleHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_news_list_news_item,parent,false));
        }else {
            return new NewsListHeadlineListHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_news_list_headlines, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_HEADER_SEARCH_ARTICLES:
                ((NewsListHeaderHolder)holder).setHeaderTitle(R.string.search_results);
                break;
            case VIEW_TYPE_HEADER_NEWEST_ARTICLES:
                ((NewsListHeaderHolder)holder).setHeaderTitle(R.string.latest_news);
                break;
            case VIEW_TYPE_HEADER_TOP_HEADLINES:
                ((NewsListHeaderHolder)holder).setHeaderTitle(R.string.top_headlines);
                break;
            case VIEW_TYPE_SEARCH_RESULTS:
                ((NewsListArticleHolder)holder).bindData(queryResultsList.get(position-1));//one header before
                break;
            case VIEW_TYPE_NEWEST_ARTICLES:
                ((NewsListArticleHolder)holder).bindData(newestArticlesList.get(position-3));//two headers and one hottest news position
                break;
            case VIEW_TYPE_TOP_HEADLINES:
                ((NewsListHeadlineListHolder)holder).bindData(headlinesList);//two headers and one hottest news position
                break;
        }
    }

    @Override
    public int getItemCount() {
       if(currentType == NewsListPresenter.TYPE_QUERY){
           return queryResultsList.size() + 1; // one for search result headers
       }
       return newestArticlesList.size() + 3; //two headers, one horizontal recycler view, could be better for more cases
    }

    @Override
    public int getItemViewType(int position) {
        if (currentType == NewsListPresenter.TYPE_QUERY) {
            if (position == 0) {
                return VIEW_TYPE_HEADER_SEARCH_ARTICLES;
            } else {
                return VIEW_TYPE_SEARCH_RESULTS; //for header
            }
        }
        //Not handled cases if there is no results for one of first two endpoints
        if (position == 0) {
            return VIEW_TYPE_HEADER_TOP_HEADLINES;
        } else if (position == 2) {
            return VIEW_TYPE_HEADER_NEWEST_ARTICLES;
        } else if (position == 1) {
            return VIEW_TYPE_TOP_HEADLINES;
        } else {
            return VIEW_TYPE_NEWEST_ARTICLES;
        }
    }

    public void setSearchQueryResults(List<Article> searchResult){
        currentType = NewsListPresenter.TYPE_QUERY;
        newestArticlesList.clear();
        headlinesList.clear();
        queryResultsList.clear();
        queryResultsList.addAll(searchResult);
        notifyDataSetChanged();
    }

    public void addSearchQueryResults(List<Article> searchResult){
        currentType = NewsListPresenter.TYPE_QUERY;
        int before = queryResultsList.size()+1;
        this.queryResultsList.addAll(searchResult);
        notifyItemRangeInserted(before,queryResultsList.size());
    }

    public void addTopHeadlinesAndNewest(List<Article> topHeadlines,List<Article> newestArticles){
        currentType = NewsListPresenter.TYPE_START_SCREEN;
        queryResultsList.clear();
        this.headlinesList.addAll(topHeadlines);
        this.newestArticlesList.addAll(newestArticles);
        notifyDataSetChanged();
    }
}
