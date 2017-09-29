package com.cloudTop.starshare.ui.main.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.RegisterVerifyCodeBeen;
import com.cloudTop.starshare.utils.CountUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppApplication;
import com.cloudTop.starshare.been.EventBusMessage;
import com.cloudTop.starshare.been.RegisterReturnBeen;
import com.cloudTop.starshare.been.WXUserInfoEntity;
import com.cloudTop.starshare.helper.CheckHelper;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIException;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.MD5Util;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.utils.ViewConcurrencyUtils;
import com.cloudTop.starshare.widget.CheckException;
import com.cloudTop.starshare.widget.WPEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * #75
 * #76
 * Created by Administrator on 2017/5/9.
 * 注册
 */

public class RegisterUserActivity extends BaseActivity {
    @Bind(R.id.userNameEditText)
    WPEditText userNameEditText;
    @Bind(R.id.msgEditText)
    WPEditText msgEditText;
    @Bind(R.id.passwordEditText)
    WPEditText passwordEditText;
    @Bind(R.id.registerButton)
    Button registerButton;
    @Bind(R.id.tv_retrieve_password)
    TextView tv_retrieve_password;
    @Bind(R.id.registerText)
    TextView registerText;
    @Bind(R.id.ll_wx_login)
    LinearLayout wxLogin;
    @Bind(R.id.tv_three_login)
    TextView threeLogin;
    private String pwd;
    private String vCode;
    private String phone;
    private String agentId;
    private Button enterStar;
    private String recommend;
    private EditText memberId;
    private EditText brokerId;
    private String sub_agentId;
    private String userMenberId;
    private EditText areaBrokerId;
    private boolean isWXBind = false;
    private WXUserInfoEntity wxUserInfo;
    private static Dialog mDetailDialog;
    private RegisterVerifyCodeBeen verifyCodeBeen;
    private CheckHelper checkHelper = new CheckHelper();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        wxUserInfo = (WXUserInfoEntity) intent.getParcelableExtra("wxBind");
        if (wxUserInfo != null) {
            registerButton.setText("微信绑定");
            isWXBind = true;
            wxLogin.setVisibility(View.GONE);
            threeLogin.setVisibility(View.GONE);
        } else {
            wxLogin.setVisibility(View.VISIBLE);
            threeLogin.setVisibility(View.VISIBLE);
        }

        initIdDialog();
        WindowManager.LayoutParams p = getWindow().getAttributes();// 获取对话框当前的参值
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        p.width = (int) (size.x * 0.85);
        getWindow().setAttributes(p); // 设置生效
        userNameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        checkHelper.checkButtonState(registerButton, userNameEditText, msgEditText, passwordEditText);
        checkHelper.checkPwdInPutType(passwordEditText.getEditText(), this);  //密码不能输入中文
        msgEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        msgEditText.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SocketAPINettyBootstrap.getInstance().isOpen()) {
                    ToastUtils.showShort("网络连接失败,请检查网络");
                    return;
                }
                //判断是否注册过
                JudgeRegister();

            }
        });
    }

    @OnClick(R.id.registerButton)
    public void registerButton() {
        ViewConcurrencyUtils.preventConcurrency();  //防止并发
        CheckException exception = new CheckException();
        phone = userNameEditText.getEditTextString();
        pwd = passwordEditText.getEditTextString();
        vCode = msgEditText.getEditTextString();
        if (checkHelper.checkMobile(phone, exception) && checkHelper.checkPassword(pwd, exception)
                && checkHelper.checkVerifyCode(vCode, exception)) {
            if (!verifyCode()) {
                return;
            }
            mDetailDialog.show();
            //会员id
        } else {
            ToastUtils.showShort(exception.getErrorMsg());
        }
    }

    //绑定微信
    private void wxBindInfo() {
        NetworkAPIFactoryImpl.getUserAPI().bindNumber(userNameEditText.getEditTextString(), wxUserInfo.getOpenid()
                , MD5Util.MD5(passwordEditText.getEditTextString()), verifyCodeBeen.getTimeStamp(), verifyCodeBeen.getVToken(), vCode,
                "", "", "", sub_agentId, ""
                , wxUserInfo.getNickname(), wxUserInfo.getHeadimgurl(), new OnAPIListener<RegisterReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.logd("微信绑定失败!");
                    }

                    @Override
                    public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                        LogUtils.logd("微信绑定成功" + registerReturnBeen.toString());
                        if (registerReturnBeen.getResult() == -301) {
                            ToastUtils.showShort("用户已经注册,请直接登录");
                            SharePrefUtil.getInstance().putLoginPhone(userNameEditText.getEditTextString());
                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in, R.anim.activity_off_top_out);
                        } else if (registerReturnBeen.getResult() == 1) {
                            ToastUtils.showShort("绑定成功");
                            SharePrefUtil.getInstance().putLoginPhone(userNameEditText.getEditTextString());
                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in, R.anim.activity_off_top_out);
                        }
                    }
                });
    }

    @OnClick(R.id.tv_retrieve_password)
    public void retrievePassword() {
        ViewConcurrencyUtils.preventConcurrency();  //防止并发
        startActivity(ResetUserPwdActivity.class);
    }

    @OnClick(R.id.registerText)
    public void doingLoging() {
        ViewConcurrencyUtils.preventConcurrency();  //防止并发
        startActivity(LoginActivity.class);
        finish();
        overridePendingTransition(R.anim.activity_open_down_in, R.anim.activity_off_top_out);
    }

    private boolean verifyCode() {
        //本地校验验证码   MD5(yd1742653sd + code_time + rand_code + phone)
        if (verifyCodeBeen == null || TextUtils.isEmpty(verifyCodeBeen.getVToken())) {
            ToastUtils.showShort("无效验证码");
            return false;
        }

        //本地校验验证码   MD5(yd1742653sd + code_time + rand_code + phone)
        if (!verifyCodeBeen.getVToken().equals(MD5Util.MD5("yd1742653sd" + verifyCodeBeen.getTimeStamp() + vCode + userNameEditText.getEditTextString()))) {
            ToastUtils.showShort("验证码错误,请重新输入");
            return false;
        } else {
            return true;
        }
    }


    private void register() {
        NetworkAPIFactoryImpl.getUserAPI().register(userNameEditText.getEditTextString(),
                MD5Util.MD5(passwordEditText.getEditTextString()), "", "", "", "", sub_agentId,
                new OnAPIListener<RegisterReturnBeen>() {
                    @Override
                    public void onError(Throwable ex) {
                        LogUtils.logd("注册请求网络失败" + ex.toString());
                    }

                    @Override
                    public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                        LogUtils.logd("注册请求网络成功" + registerReturnBeen.toString());
                        if (registerReturnBeen.getResult() == -301) {
                            ToastUtils.showShort("用户已经注册,请直接登录");
                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in, R.anim.activity_off_top_out);
                        } else if (registerReturnBeen.getResult() == 1) {
                            ToastUtils.showShort("注册成功,请登录");
                            SharePrefUtil.getInstance().putLoginPhone(userNameEditText.getEditTextString());
//                            loginGetUserInfo(newPwd);  //登录请求数据
                            startActivity(LoginActivity.class);
                            finish();
                            overridePendingTransition(R.anim.activity_open_down_in, R.anim.activity_off_top_out);
                        }
                    }
                });
    }

    private void getCode() {
        LogUtils.logd("请求网络获取短信验证码------------------------------");
        CheckException exception = new CheckException();
        String phoneEdit = userNameEditText.getEditTextString();
        if (new CheckHelper().checkMobile(phoneEdit, exception)) {
            //Utils.closeSoftKeyboard(view);
            NetworkAPIFactoryImpl.getUserAPI().verifyCode(phoneEdit, 0, new OnAPIListener<RegisterVerifyCodeBeen>() {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtils.logd("验证码请求网络错误------------------" + ((NetworkAPIException) ex).getErrorCode());
                }

                @Override
                public void onSuccess(RegisterVerifyCodeBeen o) {
                    verifyCodeBeen = o;
                    new CountUtil(msgEditText.getRightText()).start();   //收到回调才开启计时
                    LogUtils.logd("获取到--注册短信验证码,时间戳是:" + o.toString());
                }
            });
        } else {
            ToastUtils.showShort(exception.getErrorMsg());
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_off_top_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.close)
    public void close() {
        EventBus.getDefault().postSticky(new EventBusMessage(2));  //登录取消消息
        finish();
    }

    @OnClick(R.id.tv_weixin_login)
    public void weixinLogin() {
        ViewConcurrencyUtils.preventConcurrency();  //防止并发
        if (!AppApplication.api.isWXAppInstalled()) {
            ToastUtils.showShort("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        AppApplication.api.sendReq(req);

    }

    private void JudgeRegister() {
        startProgressDialog();
        CheckException exception = new CheckException();
        String phoneEdit = userNameEditText.getEditTextString();
        if (new CheckHelper().checkMobile(phoneEdit, exception)) {
            NetworkAPIFactoryImpl.getUserAPI().isRegisted(phoneEdit, new OnAPIListener<RegisterReturnBeen>() {
                @Override
                public void onError(Throwable ex) {
                    stopProgressDialog();
                    LogUtils.loge("错误--------------");
                    ToastUtils.showShort("网络异常,请检查网络连接");
                }

                @Override
                public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                    stopProgressDialog();
                    if (registerReturnBeen.getResult() == 1) {
                        if (isWXBind) {
                            getCode();
                        } else {
                            ToastUtils.showShort("手机号码已注册,请直接登录");
                        }
                    } else if (registerReturnBeen.getResult() == 0) {
                        getCode();
                    }

                }
            });
        } else {
            ToastUtils.showShort(exception.getErrorMsg());
        }
    }

    //会员id弹窗
    private void initIdDialog() {
        mDetailDialog = new Dialog(this, R.style.custom_dialog);
        mDetailDialog.setContentView(R.layout.dialog_input_id);
        brokerId = (EditText) mDetailDialog.findViewById(R.id.broker_id);
        enterStar = (Button) mDetailDialog.findViewById(R.id.btn_enter_star);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        enterStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("输入会员ID----------------------");
                if (TextUtils.isEmpty(brokerId.getText().toString().trim())) {
                    sub_agentId = "";
                } else {
                    sub_agentId = brokerId.getText().toString().trim();  //经济人人  == 推荐人
                }
                //userMenberId = memberId.getText().toString().trim();
                //agentId = areaBrokerId.getText().toString().trim();//区域。。经纪人
                mDetailDialog.dismiss();
                if (isWXBind) {
                    wxBindInfo();
                } else {
                    register();
                }
            }
        });


    }
}
