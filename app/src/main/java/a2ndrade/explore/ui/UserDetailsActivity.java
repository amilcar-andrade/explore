package a2ndrade.explore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import a2ndrade.explore.R;
import a2ndrade.explore.data.api.GitHubService;
import a2ndrade.explore.data.model.User;
import a2ndrade.explore.util.glide.CircleTransform;
import butterknife.BindView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserDetailsActivity extends AbstractBaseActivity {
    private static final String BUNDLE_USER = "BUNDLE_USER";
    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    public static final String EXTRA_AVATAR_URL = "EXTRA_AVATAR_URL";

    @BindView(R.id.developer_avatar)
    ImageView avatar;
    CircleTransform circleTransform;
    User user;

    @Override
    int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    void onCreate0(Bundle savedInstanceState) {
        circleTransform = new CircleTransform(this);
        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable(BUNDLE_USER);
            bindUser();
        }

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_USER_NAME) &&
                intent.hasExtra(EXTRA_AVATAR_URL)&& user == null) {
            final String username = intent.getStringExtra(EXTRA_USER_NAME);
            final String avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL);
            loadUserImage(avatarUrl);
            loaderUser(username);
        }
    }

    @Override
    protected void onDestroy() {
        Glide.clear(avatar);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_USER, user);
    }

    void bindUser() {
        if (user == null) {
            return;
        }

    }

    private void loadUserImage(String avatarUrl) {
        Glide.with(UserDetailsActivity.this)
                .load(avatarUrl)
                .transform(circleTransform)
                .placeholder(R.drawable.avatar_placeholder)
                .into(avatar);
    }

    private void loaderUser(String username) {
        GitHubService gitHubApi = new RestAdapter.Builder()
                .setEndpoint(GitHubService.BASE_END_POINT)
                .build()
                .create(GitHubService.class);
        gitHubApi.getUser(username, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                UserDetailsActivity.this.user = user;
                bindUser();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}