package a2ndrade.explore.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import a2ndrade.explore.R;
import a2ndrade.explore.data.background.DevelopersTrendingLoader;
import a2ndrade.explore.data.background.ReposTrendingLoader;
import a2ndrade.explore.data.model.Developer;
import a2ndrade.explore.data.model.Repo;
import a2ndrade.explore.ui.adapters.DevelopersAdapter;
import a2ndrade.explore.ui.adapters.ReposAdapter;
import a2ndrade.explore.ui.recyclerview.InsetDividerDecoration;
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

    private static final Set<Class> SET_DIVIDERS;
    static {
        Set<Class> set = new HashSet<>(2);
        set.add(ReposAdapter.RepoViewHolder.class);
        set.add(DevelopersAdapter.DevelopersViewHolder.class);
        SET_DIVIDERS = set;
    }

    @BindString(R.string.trending_interval_month) String month;
    @BindString(R.string.trending_interval_today) String today;
    @BindString(R.string.trending_interval_week) String week;
    @BindView(R.id.repos_list) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(android.R.id.empty) View empty;
    @BindView(R.id.trending_empty_icon) ImageView emptyIcon;
    @BindView(R.id.trending_empty_title) TextView emptyTextView;

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
        final View view = inflater.inflate(R.layout.fragment_trending_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        final Resources res = getResources();
        recyclerView.addItemDecoration(new InsetDividerDecoration(
                SET_DIVIDERS,
                res.getDimensionPixelSize(R.dimen.divider_height),
                res.getDimensionPixelSize(R.dimen.keyline_2),
                ContextCompat.getColor(getContext(), R.color.divider)));
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
                onLoaderResetInternal(MODE_DEVELOPERS);
            }

            @Override
            public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> data) {
                onLoadFinishedInternal(data);
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
                onLoaderResetInternal(MODE_REPOS);
            }

            @Override
            public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> data) {
                onLoadFinishedInternal(data);
                loader.cancelLoad();
            }
        };

        getLoaderManager().initLoader(mode, createLoaderBundle(language, timeFrame), (mode == MODE_DEVELOPERS ? developersCallback : reposCallback));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        developersCallback = null;
        reposCallback = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void onLoadFinishedInternal(List<?> data) {
        if (!isAdded()) {
            return;
        }

        final boolean isDevelopersMode = mode == MODE_DEVELOPERS;
        progressBar.setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            emptyIcon.setImageResource(isDevelopersMode ? R.drawable.ic_action_account_circle : R.drawable.ic_social_domain);
            emptyTextView.setText(isDevelopersMode ? R.string.developers_empty_title : R.string.repos_empty_title);
            empty.setVisibility(View.VISIBLE);
            return;
        }

        if (isDevelopersMode) {
            developersAdapter.addItems((List<Developer>) data, timeFrame);
        } else  {
            reposAdapter.addItems((List<Repo>) data, timeFrame);
        }
        recyclerView.setAdapter(isDevelopersMode ? developersAdapter : reposAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void onLoaderResetInternal(int mode) {
        // Loader reset, throw away our data,
        // unregister any listeners, etc.
        if (mode == MODE_DEVELOPERS) {
            developersAdapter.clearItems();
        } else {
            reposAdapter.clearItems();
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    private Bundle createLoaderBundle(String language, String newTimeFrame) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_LANGUAGE_KEY, language);
        bundle.putString(BUNDLE_TIME_FRAME_KEY, newTimeFrame);
        return bundle;
    }

    private void refreshByTimeFrame(MenuItem item, String newTimeFrame) {
        final LoaderManager loaderManager = getLoaderManager();
        loaderManager.destroyLoader(mode == MODE_DEVELOPERS ? LOADER_DEVELOPERS_ID : LOADER_REPOS_ID);
        item.setChecked(true);
        timeFrame = newTimeFrame;
        loaderManager.restartLoader(mode == MODE_DEVELOPERS ? LOADER_DEVELOPERS_ID : LOADER_REPOS_ID, createLoaderBundle(language, timeFrame),
                mode == MODE_REPOS ? reposCallback : developersCallback);
    }
}
