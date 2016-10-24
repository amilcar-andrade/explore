package a2ndrade.explore.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.IntegrationCategory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegrationCategoryAdapter extends RecyclerView.Adapter<IntegrationCategoryAdapter.IntegrationCategoryHolder> {

    private final LayoutInflater inflater;
    private final List<IntegrationCategory> items;

    public IntegrationCategoryAdapter(List<IntegrationCategory> items, LayoutInflater inflater) {
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public IntegrationCategoryAdapter.IntegrationCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegrationCategoryAdapter.IntegrationCategoryHolder(inflater.inflate(R.layout.integration_category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IntegrationCategoryAdapter.IntegrationCategoryHolder holder, int position) {
        final IntegrationCategory category = items.get(position);
        holder.name.setText(category.name);
        final IntegrationAdapter adapter = new IntegrationAdapter(category.integrations, inflater);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*package*/ static class IntegrationCategoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.integration_name) TextView name;
        @BindView(R.id.integration_recycler) RecyclerView recyclerView;

        /* package */ IntegrationCategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}