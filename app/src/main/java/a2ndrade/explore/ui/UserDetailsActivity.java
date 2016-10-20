package a2ndrade.explore.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import a2ndrade.explore.R;
import a2ndrade.explore.data.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends AppCompatActivity {
    private final static String EXTRA_USER = "EXTRA_USER";
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.blog)
    TextView blog;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable(BUNDLE_KEY_USER);
        } else if (intent.hasExtra(EXTRA_USER)) {
            user = intent.getParcelableExtra(EXTRA_USER);
        }

        toolbarLayout.setTitle(user.name);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        email.setText(user.email);
        company.setText(user.company);
        blog.setText(user.blog);
        bio.setText(user.bio);
        Glide.with(this)
                .load(user.avatar_url)
                .placeholder(R.drawable.avatar_placeholder)
                .into(avatar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_USER, user);
    }

    static void startUserDetailsActivity(Activity host, User user) {
        Intent intent = new Intent(host, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_USER, user);
        host.startActivity(intent);
    }
}
