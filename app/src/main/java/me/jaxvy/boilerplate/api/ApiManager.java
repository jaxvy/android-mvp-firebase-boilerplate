package me.jaxvy.boilerplate.api;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jaxvy.boilerplate.BoilerplateApplication;
import me.jaxvy.boilerplate.api.request.BaseNetworkRequest;
import me.jaxvy.boilerplate.persistence.SharedPrefs;

public class ApiManager {

    /**
     * Refreshing the Firebase auth token every 55 min just to be on the safe side
     */
    private final static long AUTH_TOKEN_REFRESH_TIMEOUT_IN_MS = 55 * 60 * 1000;

    private Context mContext;
    private SharedPrefs mSharedPrefs;
    private CompositeDisposable mCompositeDisposable;

    public ApiManager(Context context, SharedPrefs sharedPrefs) {
        mContext = context;
        mSharedPrefs = sharedPrefs;
        mCompositeDisposable = new CompositeDisposable();
    }

    public <T> void call(BaseNetworkRequest<T> baseNetworkRequest) {
        BoilerplateApplication.getBoilerplateComponent(mContext).inject(baseNetworkRequest);
        if (refreshAuthToken()) {
            updateTokenAndExecute(baseNetworkRequest);
        } else {
            execute(baseNetworkRequest);
        }
    }

    /**
     * Firebase Auth token expires every hour. We need to refresh it before a network request if
     * it's expired.
     *
     * @return
     */
    private boolean refreshAuthToken() {
        long authTokenTimeCreate = mSharedPrefs.getAuthTokenTimeCreate();
        long now = new Date().getTime();
        return now - authTokenTimeCreate > AUTH_TOKEN_REFRESH_TIMEOUT_IN_MS;
    }

    private <T> void updateTokenAndExecute(BaseNetworkRequest<T> baseNetworkRequest) {
        Log.d("ApiManager", "Firebase auth token expired, updating...");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.getToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("ApiManager", "Firebase auth token updated");
                            String authToken = task.getResult().getToken();
                            mSharedPrefs.setAuthToken(authToken);
                            execute(baseNetworkRequest);
                        } else {
                            Log.d("ApiManager", "Firebase update auth token failed");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ApiManager", "Firebase update auth failed", e);
                    });
        } else {
            Log.d("ApiManager", "Firebase update auth token failed, user not available");
        }
    }

    private <T> void execute(BaseNetworkRequest<T> baseNetworkRequest) {
        Disposable disposable = baseNetworkRequest.getCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> baseNetworkRequest.onNext(item),
                        throwable -> baseNetworkRequest.onError(throwable),
                        () -> baseNetworkRequest.onComplete());
        mCompositeDisposable.add(disposable);
    }

    public void cancelRequests() {
        mCompositeDisposable.clear();
    }
}
