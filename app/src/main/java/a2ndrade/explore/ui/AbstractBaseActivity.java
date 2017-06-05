package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import a2ndrade.explore.ExploreApplication;
import a2ndrade.explore.data.api.GitHubApi;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractBaseActivity extends AppCompatActivity {

    @Inject
    GitHubApi service;
    private Unbinder bind;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ExploreApplication)getApplication()).getApplicationComponent().inject(this);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(AbstractBaseActivity.this);
        onCreate0(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    abstract int getLayoutId();
    abstract void onCreate0(Bundle savedInstanceState);
}
