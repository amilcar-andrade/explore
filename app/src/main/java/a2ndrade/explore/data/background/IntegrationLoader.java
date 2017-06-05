package a2ndrade.explore.data.background;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import a2ndrade.explore.ExploreApplication;
import a2ndrade.explore.data.api.IntegrationApi;
import a2ndrade.explore.data.model.IntegrationCategory;

public class IntegrationLoader extends AbstractAsyncTaskLoader<List<IntegrationCategory>> {
    @Inject
    IntegrationApi service;

    public IntegrationLoader(Context context) {
        super(context);
        ((ExploreApplication)context.getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    protected List<IntegrationCategory> loadInBackground0() throws Exception {
        return service.getIntegrations();
    }
}
