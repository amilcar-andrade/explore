package a2ndrade.explore.data.background;

import android.content.Context;

import java.util.List;

import a2ndrade.explore.data.api.GitHubService;
import a2ndrade.explore.data.model.IntegrationCategory;
import retrofit.RestAdapter;

public class IntegrationLoader extends AbstractAsyncTaskLoader<List<IntegrationCategory>> {

    public IntegrationLoader(Context context) {
        super(context);
    }

    @Override
    protected List<IntegrationCategory> loadInBackground0() throws Exception {
        GitHubService service = new RestAdapter.Builder()
                .setEndpoint(GitHubService.INTEGRATION_END_POINT)
                .build()
                .create(GitHubService.class);
        return service.getIntegrations();
    }
}
