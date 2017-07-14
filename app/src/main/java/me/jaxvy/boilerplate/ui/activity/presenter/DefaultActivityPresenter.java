package me.jaxvy.boilerplate.ui.activity.presenter;

import android.content.Context;

public class DefaultActivityPresenter extends BaseActivityPresenter<DefaultActivityPresenter.Callback> {

    public DefaultActivityPresenter(Context context) {
        super(context);
    }

    public interface Callback extends BaseActivityPresenter.Callback {

    }
}
