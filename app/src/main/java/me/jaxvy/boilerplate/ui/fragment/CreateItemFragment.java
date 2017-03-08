package me.jaxvy.boilerplate.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.fragment.presenter.CreateItemPresenter;

public class CreateItemFragment extends BaseFragment<CreateItemPresenter> implements
        CreateItemPresenter.Callback {

    @BindView(R.id.CreateItemFragment_title)
    TextInputEditText mTitleTextInputEditText;

    @BindView(R.id.CreateItemFragment_description)
    TextInputEditText mDescriptionTextInputEditText;

    public static final String TAG = CreateItemFragment.class.getName();

    public static CreateItemFragment newInstance() {
        return new CreateItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup root, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_create_item, root, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar("Create Item", true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_create_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CreateItemFragment_saveMenuItem:
                saveItem();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveItem() {
        String title = mTitleTextInputEditText.getText().toString();
        String description = mDescriptionTextInputEditText.getText().toString();
        if (!title.isEmpty() && !description.isEmpty()) {
            mPresenter.saveItem(title, description);
            hideKeyboard();
        }
    }
}
