package a2ndrade.explore.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.explore.R;

public abstract class TrendingAbstractAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<T> items = new ArrayList<>(25); // Know capacity 25
    String header;
    LayoutInflater inflater;

    TrendingAbstractAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addItems(List<T> newItems, String newHeader) {
        final int insertRangeStart = getItemCount();
        items.addAll(newItems);
        this.header = newHeader;
        notifyItemRangeInserted(insertRangeStart, newItems.size() + 1);
    }

    public void clearItems() {
        this.header = null;
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        // +1 for static header
        return items.size() + (header == null ? 0 : 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.trending_header_list_item, parent, false));
        }

        return onCreateViewHolder0(parent);
    }

    abstract RecyclerView.ViewHolder onCreateViewHolder0(ViewGroup parent);

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView listHeader;

        /* package */ HeaderViewHolder(View itemView) {
            super(itemView);
            listHeader = (TextView) itemView;
        }
    }
}
