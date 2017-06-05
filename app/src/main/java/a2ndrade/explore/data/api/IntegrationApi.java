package a2ndrade.explore.data.api;

import java.util.List;

import a2ndrade.explore.data.model.IntegrationCategory;
import retrofit.RestAdapter;
import retrofit.http.GET;

public interface IntegrationApi {
    String INTEGRATION_END_POINT = "https://gist.githubusercontent.com";

    @GET("/amilcar-andrade/4a9590f35b1636069ac74b893924db1a/raw/b380031b3bc916de05ad431451da1ea6457ffa03")
    List<IntegrationCategory> getIntegrations();

    class Factory {

        public static IntegrationApi getIntegration() {
            return new RestAdapter.Builder()
                    .setEndpoint(INTEGRATION_END_POINT)
                    .build()
                    .create(IntegrationApi.class);
        }
    }

}
