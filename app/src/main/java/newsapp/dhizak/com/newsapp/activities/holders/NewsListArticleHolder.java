package newsapp.dhizak.com.newsapp.activities.holders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import newsapp.dhizak.com.newsapp.R;
import newsapp.dhizak.com.newsapp.model.Article;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */

public class NewsListArticleHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.news_holder_title_text_view)
    TextView titleText;
    @BindView(R.id.news_holder_content_text_view)
    TextView contentText;
    @BindView(R.id.news_holder_time_and_creator_text_view)
    TextView timeCreatedAndByText;

    private Article article;

    public NewsListArticleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Article article) {
        this.article = article;
        init();
    }

    /**
     * Initiates layout fields
     */
    private void init() {
        Context context = itemView.getContext();
        PrettyTime prettyTime = new PrettyTime();
        int flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
        String source = (article.getSource() != null && article.getSource().getName() != null) ? article.getSource().getName() : article.getAuthor();
        String createdWhen = prettyTime.format(article.getPublishedAt());
        String createdBy = "";
        if (source != null) {
            createdBy = context.getString(R.string.created_by, source);
        }
        String createWhenAndBy = String.format("%s %s", createdWhen, createdBy);
        SpannableStringBuilder ssb = new SpannableStringBuilder(createWhenAndBy);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), createWhenAndBy.indexOf(createdBy), createWhenAndBy.length(), flag);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.news_list_source_color)), createWhenAndBy.indexOf(createdBy), createWhenAndBy.length(), flag);

        titleText.setText(article.getTitle());
        contentText.setText(article.getDescription());
        timeCreatedAndByText.setText(ssb);
    }


}
