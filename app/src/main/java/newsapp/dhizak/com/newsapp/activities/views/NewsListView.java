package newsapp.dhizak.com.newsapp.activities.views;

import java.util.List;

import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */
public interface NewsListView {
    void showErrorMessage(String message);
    void showMessage(String message);
    void addQueryResults(List<Article> articles);
    void showFirstScreen(List<Article> topHeadlines,List<Article> latestNews, int page);
    void showProgressBar(boolean show);
    void setQueryText(String text);
    void setTopHeadlinesAndLatestNews(List<Article> topHeadlines, List<Article> latestNews);
    void changeButtonType(int type); //could be with enums
    void setQueryResults(List<Article> queryResult);
    void showQueryEmptyError();
}
