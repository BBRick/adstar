package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.TodayEntrustReturnBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.utils.BuyHandleStatuUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TodayEntrustAdapter extends ListBaseAdapter<TodayEntrustReturnBean> {
    public TodayEntrustAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_today_entrust;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TodayEntrustReturnBean bean = mDataList.get(position);
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        TextView tv_entrust_num = holder.getView(R.id.tv_entrust_num);
        TextView tv_content_ing = holder.getView(R.id.tv_content_ing);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_bargain_num = holder.getView(R.id.tv_bargain_num);
        TextView tv_content_ed = holder.getView(R.id.tv_content_ed);

        tv_time.setText(TimeUtil.getHourMinuteSecond(bean.getPositionTime() * 1000));
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryStarList(bean.getSymbol());
        if (starInfos != null && starInfos.size() > 0) {
            name.setText(starInfos.get(0).getName());
        }
        price.setText(TimeUtil.getYD(bean.getPositionTime() * 1000));
        tv_entrust_num.setText(String.valueOf(bean.getOpenPrice()));
        tv_bargain_num.setText(String.valueOf(bean.getAmount()));
        tv_code.setText(bean.getSymbol());
        if (bean.getBuySell() == 1) {
            tv_content_ing.setText("求购");
        } else if (bean.getBuySell() == -1){
            tv_content_ing.setText("转让");
        }

        tv_content_ed.setText(BuyHandleStatuUtils.getHandleStatu(bean.getHandle()));
    }

}
