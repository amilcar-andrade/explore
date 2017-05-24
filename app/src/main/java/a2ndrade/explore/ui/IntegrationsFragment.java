package a2ndrade.explore.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.background.IntegrationLoader;
import a2ndrade.explore.data.model.Integration;
import a2ndrade.explore.data.model.IntegrationCategory;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegrationsFragment extends AbstractBaseFragment {
    private static final int LOADER_INTEGRATION_ID = 1251;
    public static final String TAG = IntegrationsFragment.class.getSimpleName();

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(android.R.id.empty)
    View empty;
    @BindView(R.id.trending_empty_icon)
    ImageView emptyIcon;
    @BindView(R.id.trending_empty_title)
    TextView emptyTextView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindString(R.string.categories)
    String toolbarTitle;

    IntegrationCategoryAdapter adapter;
    List<IntegrationCategory> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateView0(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // no-op
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.integration_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_integrations:
        }
        return  super.onOptionsItemSelected(item);
    }

    public static IntegrationsFragment newInstance() {
        Bundle args = new Bundle();
        IntegrationsFragment fragment = new IntegrationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_INTEGRATION_ID, null, integrationLoader);
        adapter = new IntegrationCategoryAdapter(getActivity());
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        integrationLoader = null;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_integrations;
    }

    /*package*/ static class IntegrationCategoryAdapter extends RecyclerView.Adapter<IntegrationCategoryAdapter.IntegrationCategoryHolder> {

        private final LayoutInflater inflater;
        private List<IntegrationCategory> items;

        /*package*/ IntegrationCategoryAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.items = new ArrayList<>();
        }

        public void addItems(List<IntegrationCategory> categories) {
            this.items.addAll(categories);
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

    /*package*/ static class IntegrationAdapter extends RecyclerView.Adapter<IntegrationAdapter.IntegrationAdapterHolder> {

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
            holder.title.setText(integration.name);
            holder.description.setText(integration.description);
            Glide.with(inflater.getContext())
                    .load(integration.avatar_url)
                    .placeholder(R.color.content_placeholder)
                    .into(holder.avatar);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        /*package*/ static class IntegrationAdapterHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title) TextView title;
            @BindView(R.id.description) TextView description;
            @BindView(R.id.avatar) ImageView avatar;

            /* package */ IntegrationAdapterHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private LoaderManager.LoaderCallbacks<List<IntegrationCategory>> integrationLoader = new LoaderManager.LoaderCallbacks<List<IntegrationCategory>>() {
        @Override
        public Loader<List<IntegrationCategory>> onCreateLoader(int id, Bundle args) {
            return new IntegrationLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<IntegrationCategory>> loader, List<IntegrationCategory> data) {
            if (!isAdded() || getView() == null) {
                return;
            }
            IntegrationsFragment.this.data = data;
            progressBar.setVisibility(View.GONE);
            adapter.addItems(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoaderReset(Loader<List<IntegrationCategory>> loader) {
            // No-operation
        }
    };
}
