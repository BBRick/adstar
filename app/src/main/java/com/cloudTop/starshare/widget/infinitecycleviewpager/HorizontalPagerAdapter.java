package com.cloudTop.starshare.widget.infinitecycleviewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.HomePageInfoBean;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.ui.main.activity.CircleFriendsActivity;
import com.cloudTop.starshare.ui.main.activity.StarSellActivity;
import com.cloudTop.starshare.ui.main.activity.StarTimeDealActivity;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.StringUtil;

import java.util.List;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<HomePageInfoBean.SymbolInfoBean> mList;
    private boolean mHaveVirtualKey;

    public HorizontalPagerAdapter(Context context, List<HomePageInfoBean.SymbolInfoBean> list, boolean haveVirtualKey) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mList = list;
        mHaveVirtualKey = haveVirtualKey;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

//    @Override
//    public int getItemPosition(final Object object) {
//        return POSITION_NONE;
//    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.view_card_item, container, false);
        final HomePageInfoBean.SymbolInfoBean infoBean = mList.get(position);
        CardView CardView = (CardView) view.findViewById(R.id.CardView);
        ImageView img_item = (ImageView) view.findViewById(R.id.img_item);
        ImageView img_desc = (ImageView) view.findViewById(R.id.img_desc);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) CardView.getLayoutParams();
        if (mHaveVirtualKey) {
            params.setMargins(DisplayUtil.dip2px(40), 0, DisplayUtil.dip2px(40), 0);
        } else {
            params.setMargins(DisplayUtil.dip2px(23), 0, DisplayUtil.dip2px(23), 0);
        }
        CardView.setLayoutParams(params);
        LogUtils.loge("infoBean.getHome_pic_tail():" + infoBean.getHome_pic_tail());
        ImageLoaderUtils.displayWithDefaultImg(mContext, img_item, infoBean.getHome_pic_tail(), R.drawable.buying_star);
        if(infoBean.getPushlish_type() == -1 || StringUtil.isEmpty(infoBean.getHome_button_pic_tail())){
            img_desc.setVisibility(View.GONE);
        }else{
            img_desc.setVisibility(View.VISIBLE);
            ImageLoaderUtils.displayWithDefaultImg(mContext, img_desc, infoBean.getHome_button_pic_tail(), R.drawable.buying_star);
        }
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckLoginUtil.checkLogin((Activity) mContext) == false) {
                    return;
                }
                switch (infoBean.getPushlish_type()) {
                    case -1:
                        Intent intent0 = new Intent(mContext, CircleFriendsActivity.class);
                        mContext.startActivity(intent0);
                        break;
                    case 0:
                        Intent intent1 = new Intent(mContext, StarSellActivity.class);
                        intent1.putExtra(AppConstant.STAR_CODE, infoBean.getSymbol());
                        intent1.putExtra(AppConstant.AUCTION_TYPE, infoBean.getWork());
                        intent1.putExtra(AppConstant.PUBLISH_TYPE, infoBean.getPushlish_type());
                        intent1.putExtra(AppConstant.IS_PRESELL, true);
                        mContext.startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(mContext, StarSellActivity.class);
                        intent2.putExtra(AppConstant.STAR_CODE, infoBean.getSymbol());
                        intent2.putExtra(AppConstant.AUCTION_TYPE, infoBean.getWork());
                        intent2.putExtra(AppConstant.PUBLISH_TYPE, infoBean.getPushlish_type());
                        mContext.startActivity(intent2);
                        break;
                    case 2:
                        StarListReturnBean.SymbolInfoBean symbolInfoBean = new StarListReturnBean.SymbolInfoBean();
                        symbolInfoBean.setSymbol(infoBean.getSymbol());
                        symbolInfoBean.setPic_tail(infoBean.getPic_tail());
                        symbolInfoBean.setName(infoBean.getName());
                        symbolInfoBean.setWid(infoBean.getWid());
                        Intent intent3 = new Intent(mContext, StarTimeDealActivity.class);
                        intent3.putExtra(AppConstant.SYMBOL_INFO_BEAN, symbolInfoBean);
                        mContext.startActivity(intent3);
                        break;
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
