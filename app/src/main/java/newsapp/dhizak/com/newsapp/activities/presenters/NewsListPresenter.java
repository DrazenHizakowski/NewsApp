package newsapp.dhizak.com.newsapp.activities.presenters;

import android.os.Bundle;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public interface NewsListPresenter {

    int TYPE_START_SCREEN = 0;
    int TYPE_QUERY = 1;

    String BUNDLE_TYPE = "type";
    String BUNDLE_QUERY = "query";
    String BUNDLE_QUERY_RESULTS = "query_results";
    String BUNDLE_TOP_HEADLINES = "top_headlines";
    String BUNDLE_LATEST_NEWS = "latest_news";

    void init(Bundle bundle);
    void onQueryChanged(String query);
    void onSaveInstanceState(Bundle bundle);
    void onLoadMoreSearchResults(String query, int page, int totalItemsCount);
    void onClearButtonClicked();
    void onPause();
}
