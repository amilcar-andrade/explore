package a2ndrade.explore.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractBaseActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
