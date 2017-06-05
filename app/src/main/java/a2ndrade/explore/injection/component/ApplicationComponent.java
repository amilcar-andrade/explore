package a2ndrade.explore.injection.component;

import android.app.Application;

import javax.inject.Singleton;

import a2ndrade.explore.data.api.GitHubApi;
import a2ndrade.explore.data.api.IntegrationApi;
import a2ndrade.explore.data.background.IntegrationLoader;
import a2ndrade.explore.injection.module.ApplicationModule;
import a2ndrade.explore.ui.AbstractBaseActivity;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application application);
    void inject(AbstractBaseActivity activity);
    void inject(IntegrationLoader loader);

    GitHubApi gitHubService();
    IntegrationApi integrationApi();
}
