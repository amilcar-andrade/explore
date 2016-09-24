package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import a2ndrade.explore.R;
import a2ndrade.explore.data.background.DevelopersTrendingLoader;
import a2ndrade.explore.data.background.ReposTrendingLoader;
import a2ndrade.explore.data.model.Developer;
import a2ndrade.explore.data.model.Repo;
import a2ndrade.explore.ui.adapters.DevelopersAdapter;
import a2ndrade.explore.ui.adapters.ReposAdapter;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TrendingListFragment extends Fragment {
    private static final String TODAY = "Today";
    private static final int LOADER_DEVELOPERS_ID = 1;
    private static final int LOADER_REPOS_ID = 2;

    private static final int MODE_DEVELOPERS = 1;
    private static final int MODE_REPOS = 2;

    private static final String BUNDLE_MODE_KEY = "BUNDLE_MODE_KEY";
    private static final String BUNDLE_TIME_FRAME_KEY = "BUNDLE_TIME_FRAME_KEY";
    private static final String BUNDLE_LANGUAGE_KEY = "BUNDLE_LANGUAGE_KEY";

    @BindString(R.string.trending_interval_month)
    String month;
    @BindString(R.string.trending_interval_today)
    String today;
    @BindString(R.string.trending_interval_week)
    String week;
    @BindView(R.id.repos_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(android.R.id.empty)
    View empty;

    private int mode;
    private String timeFrame;
    private String language;
    private ReposAdapter reposAdapter;
    private DevelopersAdapter developersAdapter;

    private Unbinder unbinder;
    private LoaderManager.LoaderCallbacks<List<Developer>> developersCallback;
    private LoaderManager.LoaderCallbacks<List<Repo>> reposCallback;

    public TrendingListFragment() {
        // Required empty public constructor
    }

    public static TrendingListFragment newDevelopersInstance() {
        return newInstance(MODE_DEVELOPERS);
    }

    public static TrendingListFragment newReposInstance() {
        return newInstance(MODE_REPOS);
    }

    private static TrendingListFragment newInstance(int mode) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_MODE_KEY, mode);
        TrendingListFragment fragment = new TrendingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getArguments().getInt(BUNDLE_MODE_KEY);
        setHasOptionsMenu(true);
        timeFrame = savedInstanceState == null ? TODAY : savedInstanceState.getString(BUNDLE_TIME_FRAME_KEY);
        language = savedInstanceState == null ? "" : savedInstanceState.getString(BUNDLE_LANGUAGE_KEY);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trending_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_interval_today:
                refreshByTimeFrame(item, today);
                break;
            case R.id.action_interval_week:
                refreshByTimeFrame(item, week);
                break;
            case R.id.action_interval_month:
                refreshByTimeFrame(item, month);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final MenuItem item;
        if (timeFrame.equals(today)) {
            item = menu.findItem(R.id.action_interval_today);
        } else if (timeFrame.equals(week)) {
            item = menu.findItem(R.id.action_interval_week);
        } else {
            item = menu.findItem(R.id.action_interval_month);
        }
        item.setChecked(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_developers_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_LANGUAGE_KEY, language);
        outState.putString(BUNDLE_TIME_FRAME_KEY, timeFrame);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        reposAdapter = new ReposAdapter(layoutInflater);
        developersAdapter = new DevelopersAdapter(layoutInflater);
        developersCallback = new LoaderManager.LoaderCallbacks<List<Developer>>() {
            @Override
            public Loader<List<Developer>> onCreateLoader(int id, Bundle args) {
                return new DevelopersTrendingLoader(getActivity(), args);

            }

            @Override
            public void onLoaderReset(Loader<List<Developer>> loader) {
                // Loader reset, throw away our data,
                // unregister any listeners, etc.
                developersAdapter.clearItems();
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> data) {
                if (isAdded()) {
                    progressBar.setVisibility(View.GONE);
                    if (data == null || data.isEmpty()) {
                        empty.setVisibility(View.VISIBLE);
                        return;
                    }

                    developersAdapter.addItems(data, timeFrame);
                    recyclerView.setAdapter(developersAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                loader.cancelLoad();
            }
        };

        reposCallback = new LoaderManager.LoaderCallbacks<List<Repo>>() {
            @Override
            public Loader<List<Repo>> onCreateLoader(int id, Bundle args) {
                return new ReposTrendingLoader(getActivity(), args);

            }

            @Override
            public void onLoaderReset(Loader<List<Repo>> loader) {
                // Loader reset, throw away our data,
                // unregister any listeners, etc.
                developersAdapter.clearItems();
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> data) {
                if (isAdded()) {
                    progressBar.setVisibility(View.GONE);
                    if (data == null || data.isEmpty()) {
                        empty.setVisibility(View.VISIBLE);
                        return;
                    }

                    reposAdapter.addItems(data, timeFrame);
                    recyclerView.setAdapter(reposAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                loader.cancelLoad();
            }
        };

        getLoaderManager().initLoader(mode == MODE_DEVELOPERS ? MODE_DEVELOPERS : MODE_REPOS, createLoaderBundle(language, timeFrame),
                (mode == MODE_DEVELOPERS ? developersCallback : reposCallback));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        developersCallback = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Bundle createLoaderBundle(String language, String newTimeFrame) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_LANGUAGE_KEY, language);
        bundle.putString(BUNDLE_TIME_FRAME_KEY, newTimeFrame);
        return bundle;
    }

    private void refreshByTimeFrame(MenuItem item, String newTimeFrame) {
        final LoaderManager loaderManager = getLoaderManager();
        loaderManager.destroyLoader(LOADER_DEVELOPERS_ID);
        item.setChecked(true);
        timeFrame = newTimeFrame;
        loaderManager.restartLoader(LOADER_DEVELOPERS_ID, createLoaderBundle(language, timeFrame), developersCallback);
    }
}
