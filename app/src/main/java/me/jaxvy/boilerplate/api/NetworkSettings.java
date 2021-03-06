package me.jaxvy.boilerplate.api;

import java.io.IOException;
import java.util.ArrayList;

import me.jaxvy.boilerplate.BuildConfig;
import me.jaxvy.boilerplate.persistence.SharedPrefs;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkSettings {

    private SharedPrefs mSharedPrefs;

    public NetworkSettings(SharedPrefs sharedPrefs) {
        mSharedPrefs = sharedPrefs;
    }

    public ArrayList<Interceptor> getInterceptors() {
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        if (BuildConfig.DEBUG) {
            interceptors.add(getLoggingInterceptor());
            /*
            // In case we need to emulate a slow network on device
            interceptors.add(chain -> {
                Log.d("FakeNetworkSlowdown", "Sleeping for 3 seconds");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("FakeNetworkSlowdown", "Interrupted", e);
                }
                Log.d("FakeNetworkSlowdown", "Network slowdown done. Proceeding chain");
                return chain.proceed(chain.request());
            });
            */
        }
        interceptors.add(mAuthInterceptor);
        return interceptors;
    }

    public Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


    private final Interceptor mAuthInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String authToken = mSharedPrefs.getAuthToken();
            if (authToken != null) {
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("auth", authToken)
                        .build();
                request = request.newBuilder().url(url).build();
            }
            return chain.proceed(request);
        }
    };

    public String getBaseUrl() {
        return BuildConfig.BASE_SERVICE_URL;
    }
}
