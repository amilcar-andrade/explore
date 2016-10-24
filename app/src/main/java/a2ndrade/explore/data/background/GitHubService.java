package a2ndrade.explore.data.background;

import java.util.List;

import a2ndrade.explore.data.model.IntegrationCategory;
import a2ndrade.explore.data.model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {

    String END_POINT = "https://api.github.com";
    String INTEGRATION_END_POINT = "https://gist.githubusercontent.com";

    @GET("/users/{user}")
    void getUser(@Path("user") String user, Callback<User> callback);

    @GET("/users/{user}/followers")
    List<User> getFollowers(@Path("user") String user);

    @GET("/users/{user}/following")
    List<User> getFollowing(@Path("user") String user);

    @GET("/amilcar-andrade/4a9590f35b1636069ac74b893924db1a/raw/b380031b3bc916de05ad431451da1ea6457ffa03")
    List<IntegrationCategory> getIntegrations();
}