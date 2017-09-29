package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.cloudTop.starshare.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class HomePageInfoBean {
    private String home_last_pic_tail = "";
    private List<SymbolInfoBean> symbol_info;

    public List<SymbolInfoBean> getSymbol_info() {
        return symbol_info;
    }

    public void setSymbol_info(List<SymbolInfoBean> symbol_info) {
        this.symbol_info = symbol_info;
    }

    public String getHome_last_pic_tail() {
        return home_last_pic_tail;
    }

    public void setHome_last_pic_tail(String home_last_pic_tail) {
        this.home_last_pic_tail = home_last_pic_tail;
    }

    public static class SymbolInfoBean implements Parcelable {

        /**
         * change : 0
         * currentPrice : 0
         * home_button_pic :
         * home_pic : http://wx1.sinaimg.cn/mw690/4e39d498gy1ffrtvf9rbij20zk0qodqt.jpg
         * name : 林志玲
         * pchg : 0
         * pic : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
         * priceTime : 1499741959
         * pushlish_type : 0
         * star_type : 1
         * symbol : 1001
         * sysTime : 1499741959
         * wid : 1011312412824
         */

        private int change;
        private int currentPrice;
        private String home_button_pic;
        private String home_button_pic_tail;
        private String home_pic_tail = "";
        private String name;
        private int pchg;
        private String pic_tail = "";
        private int priceTime;
        private int pushlish_type;
        private int star_type;
        private String symbol;
        private int sysTime;
        private String wid;
        private String work;

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public int getChange() {
            return change;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public int getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(int currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getHome_button_pic() {
            return home_button_pic;
        }

        public void setHome_button_pic(String home_button_pic) {
            this.home_button_pic = home_button_pic;
        }

        public String getHome_pic_tail() {
            LogUtils.loge("getHome_pic_tail:" + home_pic_tail);
            return home_pic_tail;
        }

        public void setHome_pic_tail(String home_pic_tail) {
            this.home_pic_tail = home_pic_tail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPchg() {
            return pchg;
        }

        public void setPchg(int pchg) {
            this.pchg = pchg;
        }

        public String getPic_tail() {
            return pic_tail;
        }

        public void setPic_tail(String pic_tail) {
            this.pic_tail = pic_tail;
        }

        public int getPriceTime() {
            return priceTime;
        }

        public void setPriceTime(int priceTime) {
            this.priceTime = priceTime;
        }

        public int getPushlish_type() {
            return pushlish_type;
        }

        public void setPushlish_type(int pushlish_type) {
            this.pushlish_type = pushlish_type;
        }

        public int getStar_type() {
            return star_type;
        }

        public void setStar_type(int star_type) {
            this.star_type = star_type;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getSysTime() {
            return sysTime;
        }

        public void setSysTime(int sysTime) {
            this.sysTime = sysTime;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getHome_button_pic_tail() {
            return home_button_pic_tail;
        }

        public void setHome_button_pic_tail(String home_button_pic_tail) {
            this.home_button_pic_tail = home_button_pic_tail;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.change);
            dest.writeInt(this.currentPrice);
            dest.writeString(this.home_button_pic);
            dest.writeString(this.home_button_pic_tail);
            dest.writeString(this.home_pic_tail);
            dest.writeString(this.name);
            dest.writeInt(this.pchg);
            dest.writeString(this.pic_tail);
            dest.writeInt(this.priceTime);
            dest.writeInt(this.pushlish_type);
            dest.writeInt(this.star_type);
            dest.writeString(this.symbol);
            dest.writeInt(this.sysTime);
            dest.writeString(this.wid);
            dest.writeString(this.work);
        }

        public SymbolInfoBean() {
        }

        protected SymbolInfoBean(Parcel in) {
            this.change = in.readInt();
            this.currentPrice = in.readInt();
            this.home_button_pic = in.readString();
            this.home_button_pic_tail = in.readString();
            this.home_pic_tail = in.readString();
            this.name = in.readString();
            this.pchg = in.readInt();
            this.pic_tail = in.readString();
            this.priceTime = in.readInt();
            this.pushlish_type = in.readInt();
            this.star_type = in.readInt();
            this.symbol = in.readString();
            this.sysTime = in.readInt();
            this.wid = in.readString();
            this.work = in.readString();
        }

        public static final Parcelable.Creator<SymbolInfoBean> CREATOR = new Parcelable.Creator<SymbolInfoBean>() {
            @Override
            public SymbolInfoBean createFromParcel(Parcel source) {
                return new SymbolInfoBean(source);
            }

            @Override
            public SymbolInfoBean[] newArray(int size) {
                return new SymbolInfoBean[size];
            }
        };
    }
}
