package newsapp.dhizak.com.newsapp.activities.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class NewsListHeadlineHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.headline_holder_cover_image_view)
    ImageView coverImage;
    @BindView(R.id.headline_holder_title_text_view)
    TextView titleText;
    @BindView(R.id.headline_holder_news_source_text_view)
    TextView sourceText;
    @BindView(R.id.headline_holder_created_text_view)
    TextView createdText;

    public NewsListHeadlineHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindData(){

    }
}
