package com.amao.calculate.core;


import org.xutils.db.annotation.Column;

public class BaseEntity extends BaseVO {

    public static final String COL_ID = "_id";

    @Column(name = COL_ID, isId = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getViewType() {
        return 0;
    }
}
