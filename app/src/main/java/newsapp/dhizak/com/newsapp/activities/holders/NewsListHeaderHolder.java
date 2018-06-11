package newsapp.dhizak.com.newsapp.activities.holders;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class NewsListHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.news_list_header_title)
    TextView headerTitle;

    public NewsListHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setHeaderTitle(@StringRes int textResource){
        headerTitle.setText(textResource);
    }
}
