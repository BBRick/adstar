package com.cloudTop.starshare.base;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class SearchReturnbeen {
    private List<StarsinfoBean> starsinfo;

    public List<StarsinfoBean> getStarsinfo() {
        return starsinfo;
    }

    public void setStarsinfo(List<StarsinfoBean> starsinfo) {
        this.starsinfo = starsinfo;
    }

    public static class StarsinfoBean {
        /**
         * gender : 0
         * name : 大卫∙甘迪
         * pic : http://ouim6qew1.bkt.clouddn.com/17090405252259ad1c0296887.jpg
         * pic_tail : 17090405252259ad1c0296887.jpg
         * publishType : 1
         * symbol : 10237
         * wid : 10237
         * wrok : 模特
         */

        private int gender;
        private String name;
        private String pic;
        private String pic_tail;
        private int publishType;
        private String symbol;
        private String wid;
        private String wrok;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic_tail() {
            return pic_tail;
        }

        public void setPic_tail(String pic_tail) {
            this.pic_tail = pic_tail;
        }

        public int getPublishType() {
            return publishType;
        }

        public void setPublishType(int publishType) {
            this.publishType = publishType;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getWrok() {
            return wrok;
        }

        public void setWrok(String wrok) {
            this.wrok = wrok;
        }
    }

//    private List<StarsinfoBean> starsinfo;
//
//    public List<StarsinfoBean> getStarsinfo() {
//        return starsinfo;
//    }
//
//    public void setStarsinfo(List<StarsinfoBean> starsinfo) {
//        this.starsinfo = starsinfo;
//    }
//
//    public static class StarsinfoBean {
//        /**
//         * gender : 1
//         * name : 林志玲
//         * pic : http://tva2.sinaimg.cn/crop.0.0.512.512.180/4e39d498jw8f9zphpwmorj20e80e80t6.jpg
//         * symbol : 1001
//         * wid : 1011312412824
//         */
//
//        private int gender;
//        private String name;
//        private String pic;
//        private String pic_tail="";
//        private String symbol;
//        private String wid;
//
//        public int getGender() {
//            return gender;
//        }
//
//        public void setGender(int gender) {
//            this.gender = gender;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPic_tail() {
//            return pic_tail;
//        }
//
//        public void setPic_tail(String pic) {
//            this.pic_tail = pic;
//        }
//
//        public String getSymbol() {
//            return symbol;
//        }
//
//        public void setSymbol(String symbol) {
//            this.symbol = symbol;
//        }
//
//        public String getWid() {
//            return wid;
//        }
//
//        public void setWid(String wid) {
//            this.wid = wid;
//        }
//    }



}
