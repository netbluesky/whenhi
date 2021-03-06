package com.whenhi.hi.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;
import com.whenhi.hi.Constants;
import com.whenhi.hi.model.Feed;

/**
 * Created by aspsine on 16/4/28.
 */
public class DetailFragmentAdapter implements FragmentNavigatorAdapter {

    private Feed mFeed;
    private Activity mActivity;

    private WebViewFragment mWebViewFragment;
    private PicFragment mPicFragment;
    private VideoFragment mVideoFragment;
    private TextFragment mTextFragment;
    private CommentFragment mCommentFragment;
    private AdFragment mAdFragment;

    public DetailFragmentAdapter(Feed feed, Activity activity){
        mFeed = feed;
        mActivity = activity;
    }

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case Constants.DETAIL_WEBVIEW:
                mWebViewFragment = WebViewFragment.newInstance(mFeed);
                return mWebViewFragment;
            case Constants.DETAIL_PIC:
                mPicFragment = PicFragment.newInstance(mFeed);
                return mPicFragment;
            case Constants.DETAIL_VIDEO:
                mVideoFragment=  VideoFragment.newInstance(mFeed,mActivity);
                return mVideoFragment;
            case Constants.DETAIL_TEXT:
                mTextFragment = TextFragment.newInstance(mFeed);
                return mTextFragment;
            case Constants.DETAIL_COMMENT:
                mCommentFragment = CommentFragment.newInstance(mFeed);
                return mCommentFragment;
            case Constants.DETAIL_AD:
                mAdFragment = AdFragment.newInstance(mFeed);
                return mAdFragment;
        }
        return null;
    }


    public void destroy(){

        if(mPicFragment != null){
            PicFragment picFragment = (PicFragment)mPicFragment;
            picFragment.destroy();
            //mPicFragment.onDestroy();
        }

        if(mWebViewFragment != null){

            WebViewFragment webViewFragment = (WebViewFragment)mWebViewFragment;
            webViewFragment.destroy();
            // mWebViewFragment.onDestroy();
        }

        if(mVideoFragment != null){
            VideoFragment videoFragment = (VideoFragment)mVideoFragment;
            videoFragment.destroy();
           // mVideoFragment.onDestroy();
        }

        if(mTextFragment != null){
            TextFragment textFragment = (TextFragment)mTextFragment;
            textFragment.destroy();
            //mTextFragment.onDestroy();
        }

        if(mAdFragment != null){
            AdFragment adFragment = (AdFragment)mAdFragment;
            adFragment.destroy();
            //mTextFragment.onDestroy();
        }
    }

    @Override
    public String getTag(int position) {
        return String.valueOf(position);
    }

    @Override
    public int getCount() {
        return 6;
    }
}