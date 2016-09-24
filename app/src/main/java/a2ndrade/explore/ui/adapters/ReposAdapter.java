package a2ndrade.explore.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.Repo;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends TrendingAbstractAdapter<Repo> {

    public ReposAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder0(ViewGroup parent) {
        return new RepoViewHolder(inflater.inflate(R.layout.trending_repo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        if (h instanceof RepoViewHolder) {
            final Repo repo = items.get(position - 1); // adjust for header);
            RepoViewHolder holder = (RepoViewHolder)h;
            holder.name.setText(repo.name);
            holder.description.setText(repo.description);
            holder.avatar.setText(String.valueOf(repo.name.charAt(0)));
            final Drawable background = holder.avatar.getBackground();
            if (background instanceof GradientDrawable) {
                ((GradientDrawable) background).setColor(Color.GRAY);
            }
        } else if (h instanceof HeaderViewHolder) {
            ((HeaderViewHolder) h).listHeader.setText(header);
        }
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_name) TextView name;
        @BindView(R.id.repo_description) TextView description;
        @BindView(R.id.repo_letter_avatar) TextView  avatar;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}