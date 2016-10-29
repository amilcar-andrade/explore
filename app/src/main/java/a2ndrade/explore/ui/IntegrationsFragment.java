package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import a2ndrade.explore.R;

public class IntegrationsFragment extends AbstractBaseFragment {

    @Override
    int getLayoutId() {
        return R.layout.fragment_integrations;
    }

    @Override
    public void onCreateView0(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // no-op
    }

    public static IntegrationsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IntegrationsFragment fragment = new IntegrationsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
