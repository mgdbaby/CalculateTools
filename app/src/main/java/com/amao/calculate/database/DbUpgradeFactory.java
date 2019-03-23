package com.amao.calculate.database;

import org.xutils.DbManager;

/***
 *--数据库升级--
 * 请对每次升级更新加以备注说明
 */
public class DbUpgradeFactory implements DbManager.DbUpgradeListener {

    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {//主线程
        if (oldVersion < 2 && newVersion >= 2) {
            upgrade1_2(db);
        }

    }

    private void upgrade1_2(DbManager db) {
    }

}
