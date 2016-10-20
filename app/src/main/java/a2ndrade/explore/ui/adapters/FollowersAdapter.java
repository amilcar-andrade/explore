package a2ndrade.explore.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowerHolder> {

    private final LayoutInflater inflater;
    private List<User> followers = new ArrayList<>();

    public FollowersAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addItems(List<User> newItems) {
        final int insertRangeStart = getItemCount();
        followers.addAll(newItems);
        notifyItemRangeInserted(insertRangeStart, newItems.size() );
    }

    @Override
    public FollowersAdapter.FollowerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowersAdapter.FollowerHolder(inflater.inflate(R.layout.followers_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FollowersAdapter.FollowerHolder holder, int position) {
        final User user = followers.get(position);
        holder.name.setText(user.name != null ? user.name : user.login);
        holder.login.setText(user.login);
        Glide.with(inflater.getContext())
                .load(user.avatar_url)
                .placeholder(R.color.content_placeholder)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    /*package*/ static class FollowerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.login) TextView login;
        @BindView(R.id.avatar) ImageView avatar;

        /* package */ FollowerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
