package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a2ndrade.explore.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TrendingFragment extends Fragment {
    private static final int TAB_COUNT = 2;
    private static final int[] TAB_TITLES = {R.string.repositories, R.string.developers};

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trending, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return TrendingListFragment.newReposInstance();
                    case 1:
                        return TrendingListFragment.newDevelopersInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return TAB_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(TAB_TITLES[position]);
            }
        });
        tabLayout.setupWithViewPager(pager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}