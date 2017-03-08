package me.jaxvy.boilerplate.model.request;

import me.jaxvy.boilerplate.model.request.listener.OnRequestCompleteListener;
import me.jaxvy.boilerplate.model.request.listener.OnRequestErrorListener;
import me.jaxvy.boilerplate.utils.InjectionBase;
import rx.Observable;

public abstract class BaseNetworkRequest<T> extends InjectionBase {

    private Observable<T> mCall;
    private OnRequestCompleteListener mOnRequestCompleteListener;
    private OnRequestErrorListener mOnRequestErrorListener;

    public BaseNetworkRequest(Observable<T> call) {
        mCall = call;
        mOnRequestCompleteListener = null;
        mOnRequestErrorListener = null;
    }

    public BaseNetworkRequest(Observable<T> call, OnRequestCompleteListener onRequestCompleteListener) {
        mCall = call;
        mOnRequestCompleteListener = onRequestCompleteListener;
        mOnRequestErrorListener = null;
    }

    public BaseNetworkRequest(Observable<T> call, OnRequestErrorListener onRequestErrorListener) {
        mCall = call;
        mOnRequestCompleteListener = null;
        mOnRequestErrorListener = onRequestErrorListener;
    }

    public BaseNetworkRequest(Observable<T> call, OnRequestCompleteListener onRequestCompleteListener,
                              OnRequestErrorListener onRequestErrorListener) {
        mCall = call;
        mOnRequestCompleteListener = onRequestCompleteListener;
        mOnRequestErrorListener = onRequestErrorListener;
    }

    public Observable<T> getCall() {
        return mCall;
    }

    public void onComplete() {
        if (mOnRequestCompleteListener != null) {
            mOnRequestCompleteListener.onCompleted();
        }
    }

    public void onError(Throwable throwable) {
        if (mOnRequestErrorListener != null) {
            mOnRequestErrorListener.onError(throwable);
        }
    }

    public abstract void onNext(T item);
}
