package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import a2ndrade.explore.R;
import butterknife.BindView;

public class HomeActivity extends AbstractBaseActivity {
    private static final String BUNDLE_CURRENT_FRAGMENT_TAG = "BUNDLE_CURRENT_FRAGMENT_TAG";
    private static final String BUNDLE_CURRENT_MENU_ID = "BUNDLE_CURRENT_MENU_ID";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    int currentMenuId = R.id.menu_trending;
    String currentFragmentTag = TrendingFragment.TAG;

    @Override
    int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    void onCreate0(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentMenuId = savedInstanceState.getInt(BUNDLE_CURRENT_MENU_ID);
            currentFragmentTag = savedInstanceState.getString(BUNDLE_CURRENT_FRAGMENT_TAG);
            updateMenuSelection();
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            // Default fragment
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, TrendingFragment.newInstance(), currentFragmentTag).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final Menu menu = bottomNavigationView.getMenu();
                final MenuItem previousMenuItem = menu.findItem(currentMenuId);
                if (previousMenuItem != null && item.getItemId() != previousMenuItem.getItemId()) {
                    // Un-checked previous item
                    previousMenuItem.setChecked(false);
                }

                switch (item.getItemId()) {
                    case R.id.menu_trending:
                        swapFragment(TrendingFragment.TAG);
                        break;
                    case R.id.menu_integrations:
                        swapFragment(IntegrationsFragment.TAG);
                        break;
                    case R.id.menu_showcases:
                        swapFragment(ShowcasesFragment.TAG);
                        break;
                }
                currentMenuId = item.getItemId();
                bottomNavigationView.getMenu().findItem(currentMenuId).setChecked(true);
                return false;
            }
        });
    }

    void swapFragment(String newFragmentTag) {
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();
        // Get the previous fragment one and detach it instead of removing it
        final Fragment fragmentToDetach = manager.findFragmentByTag(currentFragmentTag);
        if (fragmentToDetach != null) {
            // The detach method removes the fragment from the UI, but its state is maintained by the Fragment Manager
            ft.detach(fragmentToDetach);
        }

        // Update the current fragment tag and try to find it inside FragmentManager
        currentFragmentTag = newFragmentTag;
        Fragment fragmentToAttach = manager.findFragmentByTag(currentFragmentTag);
        if (fragmentToAttach == null) {
            fragmentToAttach = TrendingFragment.TAG.equals(currentFragmentTag) ? TrendingFragment.newInstance()
                    : IntegrationsFragment.TAG.equals(currentFragmentTag) ? IntegrationsFragment.newInstance() : ShowcasesFragment.newInstance();
            ft.replace(R.id.fragment_container, fragmentToAttach, currentFragmentTag);
        }
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.attach(fragmentToAttach).commit();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_MENU_ID, currentMenuId);
        outState.putString(BUNDLE_CURRENT_FRAGMENT_TAG, currentFragmentTag);
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
