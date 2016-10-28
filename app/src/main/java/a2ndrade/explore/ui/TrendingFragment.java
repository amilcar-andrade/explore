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
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import a2ndrade.explore.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TrendingFragment extends Fragment {
    private static final int TAB_COUNT = 2;
    private static final int[] TAB_TITLES = {R.string.developers, R.string.repositories};
    private static final int[] LANGUAGE_LABELS = {R.string.language_all, R.string.language_c,
            R.string.language_java, R.string.language_javascript, R.string.language_python};

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.section_spinner)
    Spinner spinner;

    private Unbinder unbinder;

    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trending, container, false);
        unbinder = ButterKnife.bind(this, view);
        spinner.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return LANGUAGE_LABELS.length;
            }

            @Override
            public Object getItem(int position) {
                return LANGUAGE_LABELS[position];
            }

            @Override
            public long getItemId(int position) {
                return position + 1;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.trending_ab_spinner_list_item,
                            parent, false);
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(
                        getString(LANGUAGE_LABELS[position]));
                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.trending_ab_spinner_list_item_dropdown,
                            parent, false);
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(
                        getString(LANGUAGE_LABELS[position]));
                return convertView;
            }
        });

        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return TrendingListFragment.newDevelopersInstance();
                    case 1:
                        return TrendingListFragment.newReposInstance();
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
