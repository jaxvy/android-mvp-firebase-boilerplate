package me.jaxvy.boilerplate;

import android.app.Application;
import android.content.Context;

import me.jaxvy.boilerplate.utils.inject.BoilerplateComponent;
import me.jaxvy.boilerplate.utils.inject.BoilerplateModule;
import me.jaxvy.boilerplate.utils.inject.DaggerBoilerplateComponent;

public class BoilerplateApplication extends Application {

    private BoilerplateComponent mBoilerplateComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mBoilerplateComponent = DaggerBoilerplateComponent.builder()
                .boilerplateModule(new BoilerplateModule(getApplicationContext()))
                .build();
    }

    public BoilerplateComponent getBoilerplateComponent() {
        return mBoilerplateComponent;
    }

    public static BoilerplateComponent getBoilerplateComponent(Context context) {
        BoilerplateApplication boilerplateApplication = (BoilerplateApplication) context.getApplicationContext();
        return boilerplateApplication.getBoilerplateComponent();
    }
}
