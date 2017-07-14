package me.jaxvy.boilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmResults;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.persistence.model.Item;
import me.jaxvy.boilerplate.ui.activity.callback.HomeActivityCallback;
import me.jaxvy.boilerplate.ui.adapter.ItemsAdapter;
import me.jaxvy.boilerplate.ui.activity.presenter.HomePresenter;
import me.jaxvy.boilerplate.ui.fragment.CreateItemFragment;

public class HomeActivity extends AuthenticatedActivity<HomePresenter> implements
        HomeActivityCallback,
        HomePresenter.Callback {

    @BindView(R.id.HomeActivity_itemsRecyclerView)
    RecyclerView mItemsRecyclerView;

    private ItemsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolbar("Boilerplate");

        mAdapter = new ItemsAdapter();
        mItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mItemsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @OnClick(R.id.HomeActivity_newItemFloatingActionButton)
    public void onClickNewItemFloatingActionButton() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.HomeActivity_coordinatorLayout,
                CreateItemFragment.newInstance(),
                CreateItemFragment.TAG);
        fragmentTransaction.addToBackStack(CreateItemFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.HomeActivity_signOutViewMenuItem) {
            mPresenter.signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSignOut() {
        finish();
        Intent intent = new Intent(this, LoginSignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemsChanged(RealmResults<Item> itemList) {
        mAdapter.update(itemList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(CreateItemFragment.TAG) != null) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void closeCreateItemFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(CreateItemFragment.TAG) != null) {
            fragmentManager.popBackStack();
            mPresenter.fetchItems();
        }
    }
}
