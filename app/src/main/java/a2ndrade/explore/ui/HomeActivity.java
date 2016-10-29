package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import a2ndrade.explore.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private static final String BUNDLE_CURRENT_MENU_ID = "BUNDLE_CURRENT_MENU_ID";

    @BindView(R.id.fragment_container) FrameLayout container;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    private int currentMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        final FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            currentMenuId = savedInstanceState.getInt(BUNDLE_CURRENT_MENU_ID);
            updateMenuSelection();
        }

        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            // Default fragment
            fragment = TrendingFragment.newInstance();
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final MenuItem previousMenuItem = bottomNavigationView.getMenu().findItem(currentMenuId);
                if (previousMenuItem != null && item.getItemId() != previousMenuItem.getItemId()) {
                    // Un-checked previous item
                    previousMenuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.menu_trending:
                        manager.beginTransaction().replace(R.id.fragment_container, TrendingFragment.newInstance()).commit();
                        currentMenuId = item.getItemId();
                        return true;
                    case R.id.menu_integrations:
                        manager.beginTransaction().replace(R.id.fragment_container, IntegrationsFragment.newInstance()).commit();
                        currentMenuId = item.getItemId();
                        return true;
                    case R.id.menu_showcases:
                        manager.beginTransaction().replace(R.id.fragment_container, ShowcasesFragment.newInstance()).commit();
                        currentMenuId = item.getItemId();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_MENU_ID, currentMenuId);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        // TODO: Create interface to retrieve Toolbar and title
        try {
            final FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.fragment_container);

            final Toolbar toolbar = (Toolbar) fragment.getView().findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            // TODO: Fix this
        }

    }

    private void updateMenuSelection() {
        // Instead of traversing the menu, check for the latest current menu id
        if (R.id.menu_trending == currentMenuId) {
            // We know this is the first option so do nothing
            return;
        }

        final Menu menu = bottomNavigationView.getMenu();
        // Force the menu to not be selected
        final MenuItem firstMenuItem = menu.findItem(R.id.menu_trending);
        if (firstMenuItem != null) {
            firstMenuItem.setChecked(false);
        }

        final MenuItem currentMenuItem = menu.findItem(currentMenuId);
        if (currentMenuItem != null) {
            currentMenuItem.setChecked(true);
        }
    }
}
