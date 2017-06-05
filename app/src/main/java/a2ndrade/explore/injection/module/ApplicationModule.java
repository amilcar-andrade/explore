package a2ndrade.explore.injection;

import android.app.Application;

import javax.inject.Singleton;

import a2ndrade.explore.data.api.GitHubService;
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
    GitHubService providesGitHubApi() {
        return GitHubService.Factory.build();
    }
}
