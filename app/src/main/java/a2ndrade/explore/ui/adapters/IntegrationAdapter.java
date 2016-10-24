package a2ndrade.explore.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.Integration;
import butterknife.BindView;
import butterknife.ButterKnife;

/*package*/ class IntegrationAdapter extends RecyclerView.Adapter<IntegrationAdapter.IntegrationAdapterHolder> {

    private final LayoutInflater inflater;
    private final List<Integration> items;

    /*package*/ IntegrationAdapter(List<Integration> items, LayoutInflater inflater) {
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public IntegrationAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegrationAdapterHolder(inflater.inflate(R.layout.integration_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IntegrationAdapterHolder holder, int position) {
        final Integration integration = items.get(position);
        holder.name.setText(integration.name);
        holder.description.setText(integration.description);
        Glide.with(inflater.getContext())
                .load(integration.avatar_url)
                .placeholder(android.R.color.white)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*package*/ static class IntegrationAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.avatar) ImageView avatar;

        /* package */ IntegrationAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
