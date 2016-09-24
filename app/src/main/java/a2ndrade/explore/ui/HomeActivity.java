package a2ndrade.explore.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import a2ndrade.explore.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindString(R.string.trending) String trendingTitle;
    @BindView(R.id.fragment_container) FrameLayout container;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);

        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = TrendingFragment.newInstance();
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        // TODO: Create interface to retrieve Toolbar and title
        final Toolbar toolbar = (Toolbar) fragment.getView().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(trendingTitle);
    }
}
