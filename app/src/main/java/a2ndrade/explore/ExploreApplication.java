package a2ndrade.explore;

import android.app.Application;

import a2ndrade.explore.injection.component.ApplicationComponent;
import a2ndrade.explore.injection.component.DaggerApplicationComponent;
import a2ndrade.explore.injection.module.ApplicationModule;

public class ExploreApplication extends Application {
    ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
