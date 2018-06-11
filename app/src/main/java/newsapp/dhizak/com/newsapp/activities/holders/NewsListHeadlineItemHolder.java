package newsapp.dhizak.com.newsapp.activities.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class NewsListHeadlineItemHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.headline_holder_cover_image_view)
    ImageView coverImage;
    @BindView(R.id.headline_holder_title_text_view)
    TextView titleText;
    @BindView(R.id.headline_holder_news_source_text_view)
    TextView sourceText;
    @BindView(R.id.headline_holder_created_text_view)
    TextView createdText;

    private Article article;

    public NewsListHeadlineItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindData(Article article){
        this.article = article;
        init();
    }

    private void init() {
        Context context = itemView.getContext();
        PrettyTime prettyTime = new PrettyTime();
        Glide.with(context).load(article.getUrlToImage()).into(coverImage);
        titleText.setText(article.getTitle());
        sourceText.setText(article.getSource().getName());
        createdText.setText(prettyTime.format(article.getPublishedAt()));
    }
}
