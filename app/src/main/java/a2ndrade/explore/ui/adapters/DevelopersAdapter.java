package a2ndrade.explore.ui.adapters;

import android.support.v7.widget.RecyclerView;
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

    public DevelopersAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    RecyclerView.ViewHolder onCreateViewHolder0(ViewGroup parent) {
        return new DevelopersViewHolder(inflater.inflate(R.layout.trending_developer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        if (h instanceof DevelopersViewHolder) {
            final Developer developer = items.get(position - 1); // adjust for header
            DevelopersViewHolder holder = (DevelopersViewHolder) h;
            holder.author.setText(developer.name);
            holder.description.setText(developer.description);
            Glide.with(inflater.getContext())
                    .load(developer.developerUrl)
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

        public DevelopersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}