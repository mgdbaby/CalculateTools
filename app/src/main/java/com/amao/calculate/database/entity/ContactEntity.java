package com.amao.calculate.database.entity;

import com.amao.calculate.core.BaseEntity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 联系人数据表
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/23 15:11
 */
@Table(name = "contact")
public class ContactEntity extends BaseEntity {

    public static final String COL_NAME = "name";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_HEAD_URI = "head_uri";

    @Column(name = COL_NAME)
    private String name;

    @Column(name = COL_MOBILE)
    private String mobile;

    @Column(name = COL_HEAD_URI)
    private String headUri;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadUri() {
        return headUri;
    }

    public void setHeadUri(String headUri) {
        this.headUri = headUri;
    }

    @Override
    public String toString() {
        return "ContactEntity{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headUri='" + headUri + '\'' +
                '}';
    }
}
