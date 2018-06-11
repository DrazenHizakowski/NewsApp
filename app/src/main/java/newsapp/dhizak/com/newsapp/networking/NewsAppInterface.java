package newsapp.dhizak.com.newsapp.networking;

import io.reactivex.Observable;
import newsapp.dhizak.com.newsapp.model.ArticlesEnvelope;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */
public interface NewsAppInterface {

    int pageSize  = 10;

    @GET("/v2/top-headlines?sources=bbc-news&pageSize=10&page=1")
    Observable<ArticlesEnvelope> getTopHeadlines(@Query("apiKey") String apiKey);

    @GET("/v2/top-headlines?country=us")
    Observable<ArticlesEnvelope> getTopHeadlinesForUs(@Query("apiKey") String apiKey);

    @GET("/v2/everything?pageSize="+pageSize)
    Observable<ArticlesEnvelope> getArticlesByQuery(@Query("apiKey") String apiKey, @Query("q") String query,@Query("page") int page);

}
