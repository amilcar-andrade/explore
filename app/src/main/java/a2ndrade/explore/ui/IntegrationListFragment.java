package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.background.IntegrationLoader;
import a2ndrade.explore.data.model.IntegrationCategory;
import a2ndrade.explore.ui.adapters.IntegrationCategoryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IntegrationListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<IntegrationCategory>> {
    private static final int LOADER_INTEGRATION_ID = 1251;

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(android.R.id.empty) View empty;
    @BindView(R.id.trending_empty_icon) ImageView emptyIcon;
    @BindView(R.id.trending_empty_title) TextView emptyTextView;
    @BindView(R.id.recycler) RecyclerView recyclerView;

    private Unbinder bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static IntegrationListFragment newInstance() {
        Bundle args = new Bundle();
        IntegrationListFragment fragment = new IntegrationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View v = inflater.inflate(R.layout.fragment_user_list, container, false);
        bind = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_INTEGRATION_ID, null, this);
    }

    @Override
    public Loader<List<IntegrationCategory>> onCreateLoader(int id, Bundle args) {
        return new IntegrationLoader(getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<IntegrationCategory>> loader, List<IntegrationCategory> data) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new IntegrationCategoryAdapter(data, getActivity().getLayoutInflater()));
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<IntegrationCategory>> loader) {
        // No-operation
    }
}
