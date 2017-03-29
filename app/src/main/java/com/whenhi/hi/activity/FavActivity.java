package com.whenhi.hi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.whenhi.hi.Constants;
import com.whenhi.hi.R;
import com.whenhi.hi.adapter.VideoCommentAdapter;
import com.whenhi.hi.fragment.DetailFragmentAdapter;
import com.whenhi.hi.fragment.OtherFragmentAdapter;
import com.whenhi.hi.model.BaseModel;
import com.whenhi.hi.model.Comment;
import com.whenhi.hi.model.Feed;
import com.whenhi.hi.network.HttpAPI;

public class FavActivity extends BaseActivity{

    private static final String TAG =FavActivity.class.getSimpleName();

    private FragmentNavigator mFragmentNavigator;

    private  Toolbar mToolbar;
    private TextView mTextView;
    private ActionBar mActionBar;
    private OtherFragmentAdapter mOtherFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Intent intent = getIntent();
        initView(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar).findViewById(R.id.toolbar);
        mTextView = (TextView) findViewById(R.id.toolbar).findViewById(R.id.toolbar_title);
        mTextView.setText("我的收藏");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.fanhui);
        mActionBar = getSupportActionBar();
        if (mActionBar != null){
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }

    }

    private void initView(Bundle savedInstanceState){
        mOtherFragmentAdapter = new OtherFragmentAdapter();
        mFragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), mOtherFragmentAdapter, R.id.fragment_other_fav_container);
        mFragmentNavigator.onCreate(savedInstanceState);
        setDefaultFrag();
    }


    /*设置默认Fragment*/
    private void setDefaultFrag() {
        mFragmentNavigator.showFragment(Constants.OTHER_FAV);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentNavigator = null;
        mToolbar = null;
        mTextView = null;
        mActionBar = null;
        mOtherFragmentAdapter.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}



