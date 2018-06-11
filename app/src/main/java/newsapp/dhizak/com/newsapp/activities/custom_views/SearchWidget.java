package newsapp.dhizak.com.newsapp.activities.custom_views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import newsapp.dhizak.com.newsapp.R;

/**
 * Created by Dražen Hižak on 10/06/2018.
 */
public class SearchWidget extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener{

    public static final String TYPE = "type";
    public static final int TYPE_SEARCH = 0;
    public static final int TYPE_CLEAR = 1;

    private OnSearchWidgetClickListener listener;
    private int currentType = TYPE_SEARCH;

    public SearchWidget(Context context) {
        super(context);
        setOnClickListener(this);
        setIcon();
    }

    public SearchWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        setIcon();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        currentType = ((SavedState) state).state;
        setIcon();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.state = currentType;
        return ss;
    }

    public void setType(int type){
        currentType = type;
        setIcon();
    }

    private void setIcon() {
        if(currentType == TYPE_SEARCH){
            setImageResource(R.drawable.ic_search_black_24dp);
        }else{
            setImageResource(R.drawable.ic_clear_black);
        }
    }

    @Override
    public void onClick(View view) {
        if(listener == null) return;

        if(currentType == TYPE_SEARCH){
            listener.onSearchButtonClicked();
        }else{
            listener.onClearButtonClicked();
        }
    }

    public void setListener(OnSearchWidgetClickListener listener) {
        this.listener = listener;
    }

    static class SavedState extends BaseSavedState {
        int state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            state = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public interface OnSearchWidgetClickListener{
        void onClearButtonClicked();
        void onSearchButtonClicked();
    }
}
