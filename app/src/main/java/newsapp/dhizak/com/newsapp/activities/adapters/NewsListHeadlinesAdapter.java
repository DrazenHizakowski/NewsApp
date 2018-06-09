package newsapp.dhizak.com.newsapp.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import newsapp.dhizak.com.newsapp.activities.holders.NewsListHeadlineHolder;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class NewsListHeadlinesAdapter extends RecyclerView.Adapter<NewsListHeadlineHolder> {

    @NonNull
    @Override
    public NewsListHeadlineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHeadlineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
