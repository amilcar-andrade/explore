package a2ndrade.explore.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.Developer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DevelopersAdapter extends TrendingAbstractAdapter<Developer> {
    private static final char EM_DASH = "\u2014".toCharArray()[0];

    public DevelopersAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    RecyclerView.ViewHolder onCreateViewHolder0(ViewGroup parent) {
        return new DevelopersViewHolder(inflater.inflate(R.layout.trending_developer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        final Context context = inflater.getContext();
        if (h instanceof DevelopersViewHolder) {
            ContextCompat.getColor(context, R.color.text_secondary_dark);
            final Developer developer = items.get(position - 1); // adjust for header
            DevelopersViewHolder holder = (DevelopersViewHolder) h;
            holder.author.setText(developer.name);
            Spannable span = new SpannableString(developer.repoName + " " + Character.toString(EM_DASH) + " " + developer.description);
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_secondary_dark)), 0, developer.repoName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.description.setText(span);
            Glide.with(context)
                    .load(developer.avatar_url)
                    .placeholder(R.color.content_placeholder)
                    .into(holder.avatar);
        } else if (h instanceof HeaderViewHolder) {
            ((HeaderViewHolder) h).listHeader.setText(header);
        }
    }

    public static class DevelopersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.developer_author) TextView author;
        @BindView(R.id.developer_avatar) ImageView avatar;
        @BindView(R.id.developer_description) TextView description;

        /* package */ DevelopersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}