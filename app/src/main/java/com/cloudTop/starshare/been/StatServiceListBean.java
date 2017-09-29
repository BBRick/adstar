package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sll on 2017/6/15.
 */

public class StatServiceListBean implements Parcelable{

    /**
     * list : [{"mid":"10","url1":"http://wx.qlogo.cn/mmopen/VUhlSA/0","url2":"http://wx.qlogo.cn/mmopen/VA63PGpVUhlSA/1","name":"服务1","price":"100"},{"mid":"11","url1":"http://wx.qlogo.cn/mmopen/FlVA63PGpVUhlSA/2","url2":"http://wx.qlogo.cn/mmopen/3CFlVA63PGpVUhlSA/3","name":"服务2","price":"200"}]
     * result : 1
     */

    private int result;
    private List<ListBean> list;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * mid : 10
         * url1 : http://wx.qlogo.cn/mmopen/VUhlSA/0
         * url2 : http://wx.qlogo.cn/mmopen/VA63PGpVUhlSA/1
         * name : 服务1
         * price : 100
         */

        private String mid;
        private String url1;
        private String url1_tail="";
        private String url2;
        private String url2_tail="";
        private String name;
        private String price;
        private String enddate;
        private String startdate;
        private String meet_city;

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getMeet_city() {
            return meet_city;
        }

        public void setMeet_city(String meet_city) {
            this.meet_city = meet_city;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUrl1_tail() {
            return url1_tail;
        }

        public void setUrl1_tail(String url1_tail) {
            this.url1_tail = url1_tail;
        }

        public String getUrl2_tail() {
            return url2_tail;
        }

        public void setUrl2_tail(String url2_tail) {
            this.url2_tail = url2_tail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "mid='" + mid + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", url2='" + url2 + '\'' +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", enddate='" + enddate + '\'' +
                    ", startdate='" + startdate + '\'' +
                    ", meet_city='" + meet_city + '\'' +
                    '}';
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeList(this.list);
    }

    public StatServiceListBean() {
    }

    protected StatServiceListBean(Parcel in) {
        this.result = in.readInt();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Creator<StatServiceListBean> CREATOR = new Creator<StatServiceListBean>() {
        @Override
        public StatServiceListBean createFromParcel(Parcel source) {
            return new StatServiceListBean(source);
        }

        @Override
        public StatServiceListBean[] newArray(int size) {
            return new StatServiceListBean[size];
        }
    };

    @Override
    public String toString() {
        return "StatServiceListBean{" +
                "result=" + result +
                ", list=" + list +
                '}';
    }
}
