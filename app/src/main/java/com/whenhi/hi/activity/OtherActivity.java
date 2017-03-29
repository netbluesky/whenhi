package com.whenhi.hi.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.whenhi.hi.App;
import com.whenhi.hi.R;
import com.whenhi.hi.model.BaseModel;
import com.whenhi.hi.model.Feed;
import com.whenhi.hi.network.HttpAPI;
import com.whenhi.hi.receiver.NoticeTransfer;
import com.whenhi.hi.util.ClickUtil;

import java.lang.ref.WeakReference;

/**
 * Created by 王雷 on 2017/1/5.
 */

public class OtherActivity extends BaseActivity {

    public static final String TAG = OtherActivity.class.getSimpleName();

    private int type;
    private String mobile;
    private String imageUrl;
    private String contentText;
    private int smsType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt("type");
        smsType = bundle.getInt("smsType");
        mobile = bundle.getString("mobile");
        imageUrl = bundle.getString("imageUrl");
        contentText = bundle.getString("contentText");



        show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(R.anim.activity_close,0);
    }

    private void show(){
        if(type == 1){
            smsCode();
        }else if(type == 2){
            userCode();
        }else if(type == 3){
            caidan();
        }

    }


    private void caidan(){
        Holder holder = new ViewHolder(R.layout.layout_dialog_caidan);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setCancelable(false)
                .setGravity(Gravity.CENTER)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                        finish();
                    }
                })
                .setExpanded(false)//设置扩展模式可控制dialog的高度

                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.caidan_ok:
                                if(!App.isLogin()){
                                    ClickUtil.goToLogin(view);
                                }else{
                                    Feed feed = new Feed();//彩蛋type=2
                                    feed.setType(2);
                                    feed.setTitle("每月最多领取150元话费");
                                    feed.setContent("很嗨-汇聚全世界最优质最搞笑的内容");
                                    String invitePageUrl = App.getInvitePageUrl();
                                    String userLogo = App.getUserLogo();

                                    feed.setShareUrl(invitePageUrl);
                                    feed.setImageUrl(userLogo);
                                    ClickUtil.goToShare(view, feed);
                                }

                                break;
                            case R.id.caidan_close:
                                dialog.dismiss();
                                break;
                        }

                    }
                })
                .create();
        dialog.show();

        ImageView imageCaidan =  (ImageView) holder.getInflatedView().findViewById(R.id.caidan_image);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageCaidan);
        ImageView target = imageViewWeakReference.get();
        if (target != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.bg_image)
                    .into(target);
        }

        TextView textCaidan = (TextView)holder.getInflatedView().findViewById(R.id.caidan_text);
        textCaidan.setText(contentText);

        if(!App.isLogin()){
            TextView okCaidan = (TextView)holder.getInflatedView().findViewById(R.id.caidan_ok);
            okCaidan.setText("登录领取");
        }
    }


    /**
     * 短信验证
     */
    private void smsCode(){
        final Holder holder = new ViewHolder(R.layout.layout_dialog_sms);



        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setCancelable(false)
                .setGravity(Gravity.CENTER)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                        finish();
                    }
                })
                .setExpanded(false)//设置扩展模式可控制dialog的高度

                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final DialogPlus dialog, final View view) {
                        switch (view.getId()) {
                            case R.id.sms_close:
                                dialog.dismiss();
                                break;
                            case R.id.sms_request:
                                final EditText editTextMobile = (EditText) holder.getInflatedView().findViewById(R.id.sms_phone_text);
                                final TextView codeButton = (TextView)holder.getInflatedView().findViewById(R.id.sms_request);
                                mobile = editTextMobile.getText().toString();
                                if(!TextUtils.isEmpty(mobile)){
                                    codeButton.setText("正在发送");
                                    HttpAPI.sendCode(mobile,new HttpAPI.Callback<BaseModel>() {
                                        @Override
                                        public void onSuccess(BaseModel baseModel) {
                                            if(baseModel.getState() == 0){
                                                codeButton.setText("发送成功");

                                            }else{
                                                codeButton.setText("发送验证码");
                                                Toast.makeText(OtherActivity.this, baseModel.getMsgText(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            editTextMobile.setText("服务器出事了");
                                        }
                                    });
                                }else{
                                    Toast.makeText(OtherActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            case R.id.sms_ok:
                                final EditText editTextCode = (EditText) holder.getInflatedView().findViewById(R.id.sms_code_text);
                                String code = editTextCode.getText().toString();
                                if(!TextUtils.isEmpty(code) && !TextUtils.isEmpty(mobile)){
                                    HttpAPI.inviteSMSCode(code,mobile,new HttpAPI.Callback<BaseModel>() {
                                        @Override
                                        public void onSuccess(BaseModel baseModel) {
                                            if(baseModel.getState() == 0){
                                                NoticeTransfer.mobile(mobile);
                                                if(smsType == 1){
                                                    goToPhone(view,mobile);
                                                }else if(smsType == 2){
                                                    smsType = 1;
                                                    userCode();

                                                }

                                                dialog.dismiss();

                                            }else{
                                                Toast.makeText(OtherActivity.this, baseModel.getMsgText(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            editTextCode.setText("服务器出事了");
                                        }
                                    });
                                }else{
                                    Toast.makeText(OtherActivity.this, "手机号或者验证码不能为空", Toast.LENGTH_SHORT).show();
                                }


                                break;
                        }

                    }
                })
                .create();
        dialog.show();

        if(smsType == 2){
            TextView title =  (TextView) holder.getInflatedView().findViewById(R.id.sms_title_text);
            title.setText("为了您保障您和邀请人的权益需要您验证手机号码 ");
        }



    }

    private void goToPhone(View view, String mobile){

        Intent intent = new Intent(this, ChargeActivity.class);

        Bundle bundle=new Bundle();
        //传递name参数为tinyphp
        bundle.putString("mobile", mobile);
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);

    }

    private void userCode(){

        if(smsType == 2){
            smsCode();
        }else if(smsType == 1){
            final Holder holder = new ViewHolder(R.layout.layout_dialog_code);

            final DialogPlus dialog = DialogPlus.newDialog(this)
                    .setContentHolder(holder)
                    .setCancelable(false)
                    .setGravity(Gravity.CENTER)
                    .setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogPlus dialog) {
                            finish();
                        }
                    })
                    .setExpanded(false)//设置扩展模式可控制dialog的高度

                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(final DialogPlus dialog, View view) {
                            switch (view.getId()) {
                                case R.id.code_close:
                                    dialog.dismiss();
                                    break;
                                case R.id.code_ok:
                                    EditText codeEdit = (EditText) holder.getInflatedView().findViewById(R.id.code_text);
                                    String code = codeEdit.getText().toString();
                                    if(!TextUtils.isEmpty(code)){
                                        HttpAPI.inviteCode(code,new HttpAPI.Callback<BaseModel>() {
                                            @Override
                                            public void onSuccess(BaseModel baseModel) {
                                                if(baseModel.getState() == 0){
                                                    dialog.dismiss();

                                                }else{
                                                    Toast.makeText(OtherActivity.this, baseModel.getMsgText(), Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Exception e) {
                                                Toast.makeText(OtherActivity.this, "邀请码验证系统处理点小问题", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else{
                                        Toast.makeText(OtherActivity.this, "邀请码不能为空", Toast.LENGTH_SHORT).show();
                                    }

                                    break;
                            }

                        }
                    })
                    .create();
            dialog.show();
        }
    }



}