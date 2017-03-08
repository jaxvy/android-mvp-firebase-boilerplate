package me.jaxvy.boilerplate.utils;

import android.content.Context;

import javax.inject.Inject;

import io.realm.RealmConfiguration;
import me.jaxvy.boilerplate.BoilerplateApplication;
import me.jaxvy.boilerplate.network.Api;
import me.jaxvy.boilerplate.network.ApiManager;

/**
 * Dagger cannot inject into classes with generic paramters. Theregore using this base class to
 * perform the injections
 */
public class InjectionBase {

    @Inject
    protected SharedPrefs mSharedPrefs;

    @Inject
    protected Api mApi;

    @Inject
    protected ApiManager mApiManager;

    @Inject
    protected RealmConfiguration mRealmConfiguration;

    /**
     * This default constructor is used by the BaseNetworkRequest so that
     * injection can happen later, before making the network request
     */
    public InjectionBase() {
    }

    public InjectionBase(Context context) {
        BoilerplateApplication.getBoilerplateComponent(context).inject(this);
    }
}
