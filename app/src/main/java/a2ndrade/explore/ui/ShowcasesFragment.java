package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a2ndrade.explore.R;

public class ShowcasesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_showcases, container, false);
    }

    public static ShowcasesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ShowcasesFragment fragment = new ShowcasesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
