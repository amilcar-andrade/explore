package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import a2ndrade.explore.R;

public class ShowcasesFragment extends AbstractBaseFragment {

    public static final String TAG = ShowcasesFragment.class.getSimpleName();;

    @Override
    public void onCreateView0(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // no-op
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_showcases;
    }

    public static ShowcasesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ShowcasesFragment fragment = new ShowcasesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
