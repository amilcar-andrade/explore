package a2ndrade.explore.data.background;

import java.util.List;

import a2ndrade.explore.data.model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {

    String END_POINT = "https://api.github.com";

    @GET("/users/{user}")
    void getUser(@Path("user") String user, Callback<User> callback);

    @GET("/users/{user}/followers")
    List<User> getFollowers(@Path("user") String user);

    @GET("/users/{user}/following")
    List<User> getFollowing(@Path("user") String user);
}