package newsapp.dhizak.com.newsapp.activities.presenters;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import newsapp.dhizak.com.newsapp.activities.custom_views.SearchWidget;
import newsapp.dhizak.com.newsapp.activities.views.NewsListView;
import newsapp.dhizak.com.newsapp.model.Article;
import newsapp.dhizak.com.newsapp.model.HeadlinesAndNewestEnvelope;
import newsapp.dhizak.com.newsapp.networking.RestClient;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */

public class NewsListPresenterImpl implements NewsListPresenter {
    private static final String TAG = "NewsListPresenterImpl";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Article> topHeadlines = new ArrayList<>();
    private List<Article> latestNews = new ArrayList<>();
    private List<Article> queryResult = new ArrayList<>();

    private NewsListView view;
    private String currentQuery;
    private int currentType;

    public NewsListPresenterImpl(NewsListView newsList) {
        this.view = newsList;
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle == null) {
            init();
        } else {
            initExistingScreen(bundle);
        }
    }

    @Override
    public void onQueryChanged(String query) {
        if (query.isEmpty()) {
            view.showQueryEmptyError();
            if (currentType == TYPE_QUERY) {
                setStartScreen();
                view.changeButtonType(SearchWidget.TYPE_SEARCH);
            }
        } else {
            currentType = TYPE_QUERY;
            searchNews(query, 1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(BUNDLE_TYPE, currentType);
        bundle.putString(BUNDLE_QUERY, currentQuery);
        bundle.putParcelableArrayList(BUNDLE_TOP_HEADLINES, (ArrayList<? extends Parcelable>) topHeadlines);
        bundle.putParcelableArrayList(BUNDLE_LATEST_NEWS, (ArrayList<? extends Parcelable>) latestNews);
        bundle.putParcelableArrayList(BUNDLE_QUERY_RESULTS, (ArrayList<? extends Parcelable>) queryResult);
    }

    @Override
    public void onLoadMoreSearchResults(String query, int page, int totalItemsCount) {
        if (currentType == TYPE_QUERY) {
            searchNews(query, page);
        }
    }

    @Override
    public void onClearButtonClicked() {
        currentType = TYPE_START_SCREEN;
        view.changeButtonType(SearchWidget.TYPE_SEARCH);
        view.setQueryText("");
        setStartScreen();
    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
    }

    private void init() {
        setStartScreen();
    }

    /**
     * Restoring existing screen
     *
     * @param bundle
     */
    private void initExistingScreen(Bundle bundle) {
        currentType = bundle.getInt(BUNDLE_TYPE);
        currentQuery = bundle.getString(BUNDLE_QUERY);
        queryResult = bundle.getParcelableArrayList(BUNDLE_QUERY_RESULTS);
        topHeadlines = bundle.getParcelableArrayList(BUNDLE_TOP_HEADLINES);
        latestNews = bundle.getParcelableArrayList(BUNDLE_LATEST_NEWS);

        if (currentType == TYPE_START_SCREEN) {
            setStartScreen();
        } else {
            startQueryScreen();
        }
    }

    private void startQueryScreen() {
        view.setQueryText(currentQuery);
        view.setQueryResults(queryResult);
    }

    private void setStartScreen() {
        currentType = TYPE_START_SCREEN;
        if (topHeadlines.size() > 0 && latestNews.size() > 0) {
            view.setTopHeadlinesAndLatestNews(topHeadlines, latestNews);
            return;
        }
        view.showProgressBar(true);
        Observable<List<Article>> headlinesObservable =
                RestClient.getTopHeadlines().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observable<List<Article>> newestArticlesObservable =
                RestClient.getTopUSHeadlines().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observable<HeadlinesAndNewestEnvelope> headlinesAndNewestObservable = Observable.zip(headlinesObservable, newestArticlesObservable, new BiFunction<List<Article>, List<Article>, HeadlinesAndNewestEnvelope>() {
            @Override
            public HeadlinesAndNewestEnvelope apply(List<Article> topHeadlinesList, List<Article> newestArticlesLIst) throws Exception {
                return new HeadlinesAndNewestEnvelope(topHeadlinesList, newestArticlesLIst);
            }
        });

        Disposable networkCall = headlinesAndNewestObservable.
                subscribe(headlinesAndNewestEnvelope -> {
                    topHeadlines.addAll(headlinesAndNewestEnvelope.getHeadlines());
                    latestNews.addAll(headlinesAndNewestEnvelope.getNewest());
                    view.setTopHeadlinesAndLatestNews(topHeadlines, latestNews);
                    view.showProgressBar(false);
                }, throwable -> {
                    throwable.printStackTrace(); // here could be more friendly exception or message
                    view.showErrorMessage(throwable.getMessage()); // pretty ugly
                });
        compositeDisposable.add(networkCall);
    }

    private void searchNews(String text, int page) {
        view.showProgressBar(true);
        view.changeButtonType(SearchWidget.TYPE_CLEAR);
        Disposable disposable = RestClient.getArticlesByQuery(text, page).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(articles -> {
                    if (page > 1) {
                        queryResult.addAll(articles);
                        view.addQueryResults(articles);
                    } else {
                        queryResult.clear();
                        queryResult.addAll(articles);
                        view.setQueryResults(articles);
                    }
                    view.showProgressBar(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.showErrorMessage(throwable.getMessage());//very uggly
                    view.showProgressBar(false);
                });
        compositeDisposable.add(disposable);
    }

}
