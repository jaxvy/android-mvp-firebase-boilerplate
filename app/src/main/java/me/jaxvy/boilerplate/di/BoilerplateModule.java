package me.jaxvy.boilerplate.di;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.jaxvy.boilerplate.api.Api;
import me.jaxvy.boilerplate.api.ApiManager;
import me.jaxvy.boilerplate.api.NetworkSettings;
import me.jaxvy.boilerplate.persistence.SharedPrefs;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BoilerplateModule {

    private Context applicationContext;

    public BoilerplateModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    Context provideContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    public SharedPrefs provideSharedPrefs(Context applicationContext) {
        return new SharedPrefs(applicationContext);
    }

    @Provides
    @Singleton
    public NetworkSettings provideNetworkSettings(SharedPrefs sharedPrefs) {
        return new NetworkSettings(sharedPrefs);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(NetworkSettings networkSettings) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ArrayList<Interceptor> interceptors = networkSettings.getInterceptors();
        builder.interceptors().addAll(interceptors);
        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient, NetworkSettings networkSettings) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(networkSettings.getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    Api provideApiRetrofitService(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    ApiManager provideApiManager(Context context, SharedPrefs sharedPrefs) {
        return new ApiManager(context, sharedPrefs);
    }

    @Provides @Singleton
    RealmConfiguration provideRealmConfiguration(Context applicationContext) {
        Realm.init(applicationContext);
        return new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
