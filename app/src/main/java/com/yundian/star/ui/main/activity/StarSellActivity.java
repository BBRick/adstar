package com.yundian.star.ui.main.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.OrderReturnBeen;
import com.yundian.star.been.RefreshStarTimeBean;
import com.yundian.star.been.ResultBeen;
import com.yundian.star.been.ShoppingStarBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.CheckLoginUtil;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.JudgeIsSetPayPwd;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.TimeUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.PasswordView;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import butterknife.Bind;

import static com.yundian.star.utils.TimeUtil.dateFormatYMD;

/**
 * Created by Administrator on 2017/7/8.
 * 发售页面
 */

public class StarSellActivity extends BaseActivity {
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_star_job)
    TextView tv_star_job;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_time_start)
    TextView tv_time_start;
    @Bind(R.id.tv_time_count)
    TextView tv_time_count;
    @Bind(R.id.tv_preice)
    TextView tv_preice;
    @Bind(R.id.tv_total)
    TextView tv_total;
    @Bind(R.id.tv_sure_buy)
    TextView tv_sure_buy;
    @Bind(R.id.ed_num)
    EditText ed_num;
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
    @Bind(R.id.iv_star_bg)
    ImageView iv_star_bg;
    @Bind(R.id.imageView_icon)
    ImageView imageView_icon;
    @Bind(R.id.passwordView)
    PasswordView passwordView;
    private String starCode;
    private MyHandler myHandler;
    private int type;
    private long userId;
    private String token;
    private String starTypeInfo[] = {"网红", "娱乐明星", "体育明星", "艺人", "海外知名人士", "测试"};
    private Integer num;
    private double ask_buy_prices;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_shell;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        starCode = getIntent().getStringExtra(AppConstant.STAR_CODE);
        type = getIntent().getIntExtra(AppConstant.PUBLISH_TYPE, -1);
        nl_title.setTitleText(getString(R.string.shells));
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        initData();
        getRefreshTime();
        initListener();

    }

    private void byBuyStar() {
        NetworkAPIFactoryImpl.getInformationAPI().getByBuy(userId, token, starCode,num,ask_buy_prices, new OnAPIListener<ResultBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(ResultBeen resultBeen) {
                if (resultBeen.getResult()==1||resultBeen.getResult()==2){
                    ToastUtils.showShort("购买成功");
                }else {
                    ToastUtils.showShort("购买失败");
                }
            }
        });
    }

    private void initListener() {
        tv_sure_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float total_money = (float) (num * ask_buy_prices);
                if (total_money<=0){
                    ToastUtils.showShort("总价不能零");
                    return;
                }
                CheckLoginUtil.checkLogin(StarSellActivity.this);
                if (JudgeIsSetPayPwd.isSetPwd(StarSellActivity.this)) {
                    passwordView.setVisibility(View.VISIBLE);
                }
                //byBuyStar();
                LogUtils.loge("ask_buy_prices:"+ask_buy_prices+"num:"+num+"total_money:"+total_money);
            }
        });
        passwordView.setOnFinishInput(new PasswordView.CheckPasCallBake() {
            @Override
            public void checkSuccess(OrderReturnBeen.OrdersListBean ordersListBean, String pwd) {

            }

            @Override
            public void checkError() {

            }

            @Override
            public void checkSuccessPwd(String pwd) {
                passwordView.setVisibility(View.GONE);
                //密码正确
                byBuyStar();
            }
        });
    }

    private void initData() {
        NetworkAPIFactoryImpl.getInformationAPI().getShoppingStar(starCode, new OnAPIListener<ShoppingStarBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(ShoppingStarBean shoppingStarBean) {
                showViewData(shoppingStarBean);
            }
        });

    }

    private void showViewData(final ShoppingStarBean shoppingStarBean) {
        ImageLoaderUtils.displayWithDefaultImg(this, iv_star_bg, shoppingStarBean.getBack_pic_url(), R.drawable.rec_bg);
        ImageLoaderUtils.displaySmallPhoto(this, imageView_icon, shoppingStarBean.getHead_url());
        tv_name.setText(shoppingStarBean.getStar_name());
        tv_preice.setText(String.format(getString(R.string.times_p),shoppingStarBean.getPublish_price()));
        tv_star_job.setText(starTypeInfo[shoppingStarBean.getStar_type()%6]);
        tv_time.setText(String.format(getString(R.string.shell_time), TimeUtil.formatData(dateFormatYMD, shoppingStarBean.getPublish_begin_time()),
                TimeUtil.formatData(dateFormatYMD, shoppingStarBean.getPublish_end_time())));
        tv_num.setText(String.format(getString(R.string.shell_tolnum), shoppingStarBean.getPublish_time()));
        ed_num.setHint(String.format(getString(R.string.shell_tolnum_info), shoppingStarBean.getPublish_time()));
        tv_total.setText(String.format(getString(R.string.total_money_),0f));
        ed_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    num=0;
                }else {
                    num = Integer.valueOf(s.toString().trim());
                }
                BigDecimal bg = new BigDecimal(shoppingStarBean.getPublish_price());
                ask_buy_prices = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                float total_price = (float) (num * ask_buy_prices);
                tv_total.setText(String.format(getString(R.string.total_money_),total_price));
                LogUtils.loge(shoppingStarBean.getPublish_price()+"价格保留2位数前："+ ask_buy_prices +"后:"+String.valueOf(total_price));
                if (total_price<=0){
                    tv_sure_buy.setEnabled(false);
                }else {
                    tv_sure_buy.setEnabled(true);
                }
            }
        });
    }

    private void getRefreshTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getRefreshStar(userId, token, starCode, new OnAPIListener<RefreshStarTimeBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(RefreshStarTimeBean refreshStarTimeBean) {
                secondTime = refreshStarTimeBean.getRemainingTime();
                if (myHandler != null) {
                    myHandler.removeCallbacksAndMessages(null);
                    myHandler.sendEmptyMessage(myHandler.GRT_DATA);
                }
            }
        });
    }

    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<StarSellActivity> mActivity;

        public MyHandler(StarSellActivity sellActivity) {
            mActivity = new WeakReference<StarSellActivity>(sellActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            StarSellActivity activity = mActivity.get();
            if (activity != null && activity.isFinishing() == false) {
                switch (msg.what) {
                    case GRT_DATA:
                        activity.refreshTime();
                        break;
                }
            }
        }
    }

    private int secondTime = 0;

    private void refreshTime() {
        if (tv_time_count != null && secondTime > 0 && myHandler != null) {
            tv_time_count.setText(TimeUtil.getHMS(secondTime * 1000));
            secondTime--;
            myHandler.sendEmptyMessageDelayed(myHandler.GRT_DATA, 1 * 1000);
        } else if (tv_time_count != null && secondTime <= 0) {
            tv_time_start.setVisibility(View.GONE);
            tv_time_count.setText("未开始");
        }
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (passwordView.getVisibility() == View.VISIBLE) {
                    passwordView.setVisibility(View.GONE);
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
        }
        return super.onKeyDown(keyCode, event);
    }
}
