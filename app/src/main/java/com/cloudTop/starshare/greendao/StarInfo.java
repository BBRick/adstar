package com.cloudTop.starshare.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sll on 2017/6/16.
 */
@Entity
public class StarInfo {
    @Id
    private Long id;
    private String accid;
    private String brief;
    private String code;
    private int gender;
    private String name;
    private String phone;
    private String pic1;
    private String pic_url;
    private String pic_url_tail;

    private double price;
    @Generated(hash = 692503706)
    public StarInfo(Long id, String accid, String brief, String code, int gender,
            String name, String phone, String pic1, String pic_url,
            String pic_url_tail, double price) {
        this.id = id;
        this.accid = accid;
        this.brief = brief;
        this.code = code;
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.pic1 = pic1;
        this.pic_url = pic_url;
        this.pic_url_tail = pic_url_tail;
        this.price = price;
    }
    @Generated(hash = 859816840)
    public StarInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccid() {
        return this.accid;
    }
    public void setAccid(String accid) {
        this.accid = accid;
    }
    public String getBrief() {
        return this.brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPic_url() {
        return this.pic_url;
    }
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public String getPic1() {
        return this.pic1;
    }
    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getPic_url_tail() {
        return this.pic_url_tail;
    }
    public void setPic_url_tail(String pic_url_tail) {
        this.pic_url_tail = pic_url_tail;
    }

    @Override
    public String toString() {
        return "StarInfo{" +
                "id=" + id +
                ", accid='" + accid + '\'' +
                ", brief='" + brief + '\'' +
                ", code='" + code + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pic1='" + pic1 + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", pic_url_tail='" + pic_url_tail + '\'' +
                ", price=" + price +
                '}';
    }
}
