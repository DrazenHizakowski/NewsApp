package newsapp.dhizak.com.newsapp.model;

import java.util.List;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */

public class ArticlesEnvelope {

    private String status;
    private int totalResults;
    private List<Article> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
