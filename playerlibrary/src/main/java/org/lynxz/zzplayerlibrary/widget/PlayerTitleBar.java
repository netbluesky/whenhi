package org.lynxz.zzplayerlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lynxz.zzplayerlibrary.R;
import org.lynxz.zzplayerlibrary.controller.ITitleBarImpl;


/**
 * Created by zxz on 2016/4/28.
 */
public class PlayerTitleBar extends LinearLayout implements View.OnClickListener {

    private TextView mTvTitle;
    private ITitleBarImpl mTitleBarImpl;
    private ImageView mBack;

    public PlayerTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public PlayerTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PlayerTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayerTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.zz_video_player_title_bar, this);
        View rlBack = findViewById(R.id.rl_back);
        mBack = (ImageView)findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        rlBack.setOnClickListener(this);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }
    public void setIconBack(@DrawableRes int resId){

        mBack.setImageResource(resId);
    }

    public void setTitleBarImpl(ITitleBarImpl titleBarImpl) {
        mTitleBarImpl = titleBarImpl;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_back) {
            if (mTitleBarImpl != null) {
                mTitleBarImpl.onBackClick();
            }
        }
    }
}
