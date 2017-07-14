package me.jaxvy.boilerplate.api.request;

import io.reactivex.Observable;
import me.jaxvy.boilerplate.api.request.listener.OnRequestCompleteListener;
import me.jaxvy.boilerplate.api.request.listener.OnRequestErrorListener;
import me.jaxvy.boilerplate.utils.InjectionBase;

public abstract class BaseNetworkRequest<T> extends InjectionBase {

    private OnRequestCompleteListener mOnRequestCompleteListener;
    private OnRequestErrorListener mOnRequestErrorListener;

    public BaseNetworkRequest(OnRequestCompleteListener onRequestCompleteListener) {
        mOnRequestCompleteListener = onRequestCompleteListener;
        mOnRequestErrorListener = null;
    }

    public BaseNetworkRequest(OnRequestErrorListener onRequestErrorListener) {
        mOnRequestCompleteListener = null;
        mOnRequestErrorListener = onRequestErrorListener;
    }

    public BaseNetworkRequest(OnRequestCompleteListener onRequestCompleteListener,
                              OnRequestErrorListener onRequestErrorListener) {
        mOnRequestCompleteListener = onRequestCompleteListener;
        mOnRequestErrorListener = onRequestErrorListener;
    }

    public BaseNetworkRequest() {
        mOnRequestCompleteListener = null;
        mOnRequestErrorListener = null;
    }

    public abstract Observable<T> getCall();

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
