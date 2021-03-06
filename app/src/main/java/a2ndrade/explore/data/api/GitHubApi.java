package a2ndrade.explore.data.api;

import java.util.List;

import a2ndrade.explore.data.model.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubApi {

    String BASE_END_POINT = "https://api.github.com";

    @GET("/users/{user}")
    void getUser(@Path("user") String user, Callback<User> callback);

    @GET("/users/{user}/followers")
    List<User> getFollowers(@Path("user") String user);

    @GET("/users/{user}/following")
    List<User> getFollowing(@Path("user") String user);

    class Factory {

        public static GitHubApi getGitHubApi() {
            return new RestAdapter.Builder()
                    .setEndpoint(GitHubApi.BASE_END_POINT)
                    .build()
                    .create(GitHubApi.class);
        }
    }
}
