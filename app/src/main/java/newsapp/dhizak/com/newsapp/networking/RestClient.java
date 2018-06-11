package newsapp.dhizak.com.newsapp.networking;

import java.util.List;

import io.reactivex.Observable;
import newsapp.dhizak.com.newsapp.BuildConfig;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */
public class RestClient {

    private static NewsAppInterface newsAppApi;
    private static String apiKey;

    public static NewsAppInterface getClient() {
        if (newsAppApi == null && apiKey == null) {
            newsAppApi = RetrofitHelper.getInstance().create(NewsAppInterface.class);
            apiKey = BuildConfig.API_KEY;
        }
        return newsAppApi ;
    }

    public static Observable<List<Article>> getTopHeadlines(){
        return getClient().getTopHeadlines(apiKey).map(articlesEnvelope -> articlesEnvelope.getArticles());
    }

    public static Observable<List<Article>> getTopUSHeadlines(){
        return getClient().getTopHeadlinesForUs(apiKey).map(articlesEnvelope -> articlesEnvelope.getArticles());
    }

    public static Observable<List<Article>> getArticlesByQuery(String query,int page){
        return getClient().getArticlesByQuery(apiKey,query,page).map(articlesEnvelope -> articlesEnvelope.getArticles());
    }
}
