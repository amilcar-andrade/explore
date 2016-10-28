package a2ndrade.explore.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a2ndrade.explore.R;

public class IntegrationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_integrations, container, false);
    }

    public static IntegrationsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IntegrationsFragment fragment = new IntegrationsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
