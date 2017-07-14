package me.jaxvy.boilerplate.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import io.realm.RealmConfiguration;
import me.jaxvy.boilerplate.BoilerplateApplication;
import me.jaxvy.boilerplate.api.Api;
import me.jaxvy.boilerplate.api.ApiManager;
import me.jaxvy.boilerplate.persistence.SharedPrefs;

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

    // Injecting FirebaseAuth with dagger causes a crash
    protected FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

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
