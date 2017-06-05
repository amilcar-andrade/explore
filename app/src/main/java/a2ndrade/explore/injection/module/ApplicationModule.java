package a2ndrade.explore.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import a2ndrade.explore.data.api.GitHubApi;
import a2ndrade.explore.data.api.IntegrationApi;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    GitHubApi providesGitHubApi() {
        return GitHubApi.Factory.getGitHubApi();
    }

    @Provides
    @Singleton
    IntegrationApi providesIntegrationApi() {
        return IntegrationApi.Factory.getIntegration();
    }
}
