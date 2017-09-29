package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.base.SearchReturnbeen;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.SearchListAdapter;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/16.
 * 搜索页面
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.lrv)
    LRecyclerView lrv;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.iv_cancel)
    ImageView iv_cancel;
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title ;
    private String textsearch;
    private SearchListAdapter searchListAdapter;
    private LRecyclerViewAdapter recyclerViewAdapter;
    private List<SearchReturnbeen.StarsinfoBean> list =new ArrayList<SearchReturnbeen.StarsinfoBean>(){};
    private int userId;
    private String token;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setTitleText(R.string.search);
        nt_title.setBackVisibility(true);
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        initListener();
        initAdapter();
    }

    private void initAdapter() {
        searchListAdapter = new SearchListAdapter(this);
        recyclerViewAdapter = new LRecyclerViewAdapter(searchListAdapter);
        lrv.setAdapter(recyclerViewAdapter);
        //mRecyclerView.setHasFixedSize(true);
        lrv.setLayoutManager(new LinearLayoutManager(this));
        lrv.setPullRefreshEnabled(false);
        lrv.setLoadMoreEnabled(false);
        recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(CheckLoginUtil.checkLogin(SearchActivity.this) == false){
                    startActivity(LoginActivity.class);
                    return;
                }
                SearchReturnbeen.StarsinfoBean starsinfoBean = list.get(position);
                StarListReturnBean.SymbolInfoBean bean = new StarListReturnBean.SymbolInfoBean();
                bean.setPushlish_type(starsinfoBean.getPublishType());
                bean.setWork(starsinfoBean.getWrok());
                bean.setPic_tail(starsinfoBean.getPic_tail());
                bean.setSymbol(starsinfoBean.getSymbol());
                bean.setName(starsinfoBean.getName());
                bean.setWid(starsinfoBean.getWid());
                bean.setStar_type(1);
                toStartDetailView(bean);
            }
        });
    }


    private void toStartDetailView(StarListReturnBean.SymbolInfoBean bean){
        switch (bean.getPushlish_type()){
            case -1:
                Intent intent0 = new Intent(mContext,CircleFriendsActivity.class);
                mContext.startActivity(intent0);
                break;
            case 0:
                Intent intent1 = new Intent(mContext,StarSellActivity.class);
                intent1.putExtra(AppConstant.STAR_CODE, bean.getSymbol());
                intent1.putExtra(AppConstant.AUCTION_TYPE, bean.getWork());
                intent1.putExtra(AppConstant.PUBLISH_TYPE, bean.getPushlish_type());
                intent1.putExtra(AppConstant.IS_PRESELL,true);
                mContext.startActivity(intent1);
                break;
            case 1:
                Intent intent2 = new Intent(mContext,StarSellActivity.class);
                intent2.putExtra(AppConstant.STAR_CODE, bean.getSymbol());
                intent2.putExtra(AppConstant.AUCTION_TYPE, bean.getWork());
                intent2.putExtra(AppConstant.PUBLISH_TYPE, bean.getPushlish_type());
                mContext.startActivity(intent2);
                break;
            case 2:
                Intent intent = new Intent(SearchActivity.this, StarTimeDealActivity.class);
                intent.putExtra(AppConstant.SYMBOL_INFO_BEAN, bean);
                startActivity(intent);
                break;
        }
    }

    private void initListener() {
        iv_cancel.setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    list.clear();
                    searchListAdapter.clear();
                    recyclerViewAdapter.notifyDataSetChanged();
                    lrv.refreshComplete(10);
                    return;
                }else {
                    NetworkAPIFactoryImpl.getInformationAPI().searchStar(userId,token,s.toString(), new OnAPIListener<SearchReturnbeen>() {
                        @Override
                        public void onError(Throwable ex) {

                        }

                        @Override
                        public void onSuccess(SearchReturnbeen searchReturnbeen) {
                            LogUtils.loge(searchReturnbeen.toString());
                            list.clear();
                            searchListAdapter.clear();
                            if (searchReturnbeen.getStarsinfo()!=null&&searchReturnbeen.getStarsinfo().size()!=0){
                                list = searchReturnbeen.getStarsinfo();
                                //newsInfoAdapter.setDataList(arrayList);
                                recyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                                LogUtils.loge(list.toString());
                                searchListAdapter.addAll(list);
                                lrv.refreshComplete(list.size());
                            }else {
                                recyclerViewAdapter.notifyDataSetChanged();
                                lrv.refreshComplete(10);
                            }

                        }
                    });
                }
            }
        });

        nt_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                et_search.setText("");
                break;
        }
    }
}
