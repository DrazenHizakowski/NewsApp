package newsapp.dhizak.com.newsapp.model;

import java.util.List;

/**
 * Created by Dražen Hižak on 10/06/2018.
 */

public class HeadlinesAndNewestEnvelope {

    private List<Article> headlines;
    private List<Article> newest;

    public HeadlinesAndNewestEnvelope(List<Article> headlines, List<Article> newest) {
        this.headlines = headlines;
        this.newest = newest;
    }

    public List<Article> getHeadlines() {
        return headlines;
    }

    public List<Article> getNewest() {
        return newest;
    }
}
