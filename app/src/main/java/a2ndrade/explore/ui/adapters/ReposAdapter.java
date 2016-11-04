package a2ndrade.explore.ui.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.Repo;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends TrendingAbstractAdapter<Repo> {
    private final int[] colours;

    public ReposAdapter(LayoutInflater inflater) {
        super(inflater);
        final Resources resources = inflater.getContext().getResources();
        colours = resources.getIntArray(R.array.letter_colors);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder0(ViewGroup parent) {
        return new RepoViewHolder(inflater.inflate(R.layout.trending_repo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        if (h instanceof RepoViewHolder) {
            final Repo repo = items.get(position - 1); // adjust for header);
            RepoViewHolder holder = (RepoViewHolder) h;
            holder.name.setText(repo.name);
            holder.description.setText(repo.description);
            holder.avatar.setText(String.valueOf(repo.name.charAt(0)));
            final Drawable background = holder.avatar.getBackground();
            if (background instanceof GradientDrawable) {
                if (repo.getColor() == Repo.DEFAULT_COLOR) {
                    // Choose a better color
                    final int colour = colours[new Random().nextInt(colours.length)];
                    ((GradientDrawable) background).setColor(colour);
                    repo.setColor(colour);
                } else {
                    ((GradientDrawable) background).setColor(repo.getColor());
                }
            }
        } else if (h instanceof HeaderViewHolder) {
            ((HeaderViewHolder) h).listHeader.setText(header);
        }
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_name)
        TextView name;
        @BindView(R.id.repo_description)
        TextView description;
        @BindView(R.id.repo_letter_avatar)
        TextView avatar;

        RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}