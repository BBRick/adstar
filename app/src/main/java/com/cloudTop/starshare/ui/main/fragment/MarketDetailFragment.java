package com.cloudTop.starshare.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.activity.SearchActivity;
import com.cloudTop.starshare.ui.main.activity.StarTimeDealActivity;
import com.cloudTop.starshare.ui.main.adapter.MarketDetailAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * #75
 * #76
 * Created by Administrator on 2017/5/15.
 * 行情网红热度列表
 */

public class MarketDetailFragment extends BaseFragment {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.parent_view)
    FrameLayout parentView;

    private int userId;
    private String token;
    private LRecyclerView lrv;
    private MarketDetailAdapter marketDetailAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private static int mCurrentCounter = 0;
    private static final int REQUEST_COUNT = 10;
    private ArrayList<StarListReturnBean.SymbolInfoBean> list = new ArrayList<>();
    private ArrayList<StarListReturnBean.SymbolInfoBean> loadList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        nt_title.setBackVisibility(false);
        nt_title.setTitleText(R.string.recommend_star);
        nt_title.setRightImagSrc(R.drawable.search);
        nt_title.setRightImagVisibility(true);
        initAdpter();
        initListener();
        getData(false, 0, REQUEST_COUNT);
    }

    private void initData() {
        token = SharePrefUtil.getInstance().getToken();
        userId = SharePrefUtil.getInstance().getUserId();
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
    }

    private void initListener() {
        nt_title.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });
    }

    //获取数据
    private void getData(final boolean isLoadMore, int start, int count) {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(userId,
                token, 5, 1, start, count, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {
                        if (lrv != null) {
                            lrv.setNoMore(true);
                            if (!isLoadMore) {
                                list.clear();
                                marketDetailAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                            }
                        }
                        LogUtils.loge("网红互动返回错误码" + ex.toString());
                    }

                    @Override
                    public void onSuccess(StarListReturnBean starListReturnBean) {
                        LogUtils.loge("互动列表" + starListReturnBean.toString());
                        if (starListReturnBean == null || starListReturnBean.getSymbol_info() == null || starListReturnBean.getSymbol_info().size() == 0) {
                            if (!isLoadMore) {
                                list.clear();
                                marketDetailAdapter.clear();
                                lrv.refreshComplete(REQUEST_COUNT);
                                showErrorView(parentView, R.drawable.error_view_comment, "当前没有相关数据");
                            }else {
                                lrv.setNoMore(true);
                            }

                            return;
                        }
                        if (isLoadMore) {
                            loadList.clear();
                            loadList = starListReturnBean.getSymbol_info();
                            loadMoreData();
                        } else {
                            list.clear();
                            list = starListReturnBean.getSymbol_info();
                            showData();
                        }
                    }
                });
    }

    private void initAdpter() {
        marketDetailAdapter = new MarketDetailAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(marketDetailAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    StarListReturnBean.SymbolInfoBean symbolInfoBean = list.get(position);
                    Intent intent = new Intent(getActivity(), StarTimeDealActivity.class);
                    intent.putExtra(AppConstant.SYMBOL_INFO_BEAN, symbolInfoBean);
                    startActivity(intent);
            }
        });
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true, mCurrentCounter,REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                lrv.setNoMore(false);
                getData(false, 0, REQUEST_COUNT);
            }
        });
    }

    public void showData() {
        if (list.size() == 0) {
            showErrorView(parentView, R.drawable.error_view_comment, "暂无相关数据");
            return;
        } else {
            closeErrorView();
        }
        if (marketDetailAdapter!=null){
            marketDetailAdapter.clear();
        }
        mCurrentCounter = list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        marketDetailAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            marketDetailAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }

    @Override
    public void onDestroy() {
        if (lrv!=null){
            lrv = null ;
        }
        if (marketDetailAdapter!=null){
            marketDetailAdapter=null;
        }
        super.onDestroy();
    }
}
