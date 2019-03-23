package com.amao.calculate.database;

import android.text.TextUtils;

import com.amao.calculate.BuildConfig;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;


public class DBHelper {

    public static String DB_NAME = "calculate.db";
    public static int DB_VERSION = BuildConfig.DB_VER;

    private static DbManager dbManager;


    private static void initConfig() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();

        daoConfig.setDbName(DB_NAME);
        daoConfig.setDbVersion(DB_VERSION);
//        daoConfig.setDbDir(Environment.getExternalStorageDirectory());
        daoConfig.setDbOpenListener(new DbManager.DbOpenListener() {
            @Override
            public void onDbOpened(DbManager db) {
                // 开启WAL, 对写入加速提升巨大
//                db.getDatabase().enableWriteAheadLogging();
            }
        });
        daoConfig.setDbUpgradeListener(new DbUpgradeFactory()); // 数据库升级操作
        daoConfig.setAllowTransaction(true);
        dbManager = x.getDb(daoConfig);
    }

    public static boolean create() {
        if (dbManager == null) {
            synchronized (DBHelper.class) {
                if (dbManager == null) {
                    initConfig();
                }
            }
        }
        return (dbManager != null);
    }

    public static DbManager getDbManager() {
        return dbManager;
    }

    public static void dropTable(Class<?> entityType) {
        try {
            if (dbManager != null) {
                dbManager.dropTable(entityType);
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public static boolean tableIsExist(Class<?> entityType) {
        try {
            if (dbManager != null) {
                TableEntity<?> table = dbManager.getTable(entityType);
                return table.tableIsExist();
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return false;
    }

    public static boolean save(Object object) {
        try {
            if (dbManager != null) {
                dbManager.save(object);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite save object error.", e);
        }
        return false;
    }

    public static boolean saveBindId(Object object) {
        try {
            if (dbManager != null) {
                dbManager.saveBindingId(object);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite save object error.", e);
        }
        return false;
    }

    public static boolean saveOrUpdate(Object object) {
        try {
            if (dbManager != null) {
                dbManager.saveOrUpdate(object);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite save or update object error.", e);
        }
        return false;
    }

    public static boolean delete(Class<?> entity) {
        try {
            if (dbManager != null) {
                dbManager.delete(entity);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite delete object error.", e);
        }
        return false;
    }

    public static boolean delete(Object object) {
        try {
            if (dbManager != null) {
                dbManager.delete(object);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite delete object error.", e);
        }
        return false;
    }

    /**
     * 依据ID删除数据
     **/
    public static boolean delete(Class<?> entityType, Object idVal) {
        try {
            if (dbManager != null) {
                dbManager.deleteById(entityType, idVal);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite delete object error.", e);
        }
        return false;
    }

    public static boolean delete(Class<?> entityType, WhereBuilder builder) {
        try {
            if (dbManager != null) {
                dbManager.delete(entityType, builder);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite delete object error.", e);
        }
        return false;
    }

    public static boolean update(Object object, String... updateColumnNames) {
        try {
            if (dbManager != null) {
                dbManager.update(object, updateColumnNames);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite update object error.", e);
        }
        return false;
    }

    public static boolean update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs) {
        try {
            if (dbManager != null) {
                dbManager.update(entityType, whereBuilder, nameValuePairs);
                return true;
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite update object error.", e);
        }
        return false;
    }

    public static <T> List<T> findAll(Class<T> entityType) {
        try {
            if (dbManager != null) {
                return dbManager.findAll(entityType);
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite find all object error.", e);
        }
        return null;
    }

    public static <T> List<T> findAll(Class<T> entityType, WhereBuilder wBuilder) {
        return findAll(entityType, wBuilder, null, false, null);
    }

    public static <T> List<T> findAll(Class<T> entityType, WhereBuilder wBuilder, String orderBy, boolean desc) {
        return findAll(entityType, wBuilder, orderBy, desc, null);
    }

    public static <T> List<T> findAll(Class<T> entityType, WhereBuilder wBuilder, String orderBy, boolean desc, String groupBy) {
        try {
            if (dbManager != null && tableIsExist(entityType)) {
                Selector<T> selector = dbManager.selector(entityType);
                if (wBuilder != null) {
                    selector.where(wBuilder);
                }
                if (orderBy != null) {
                    selector.orderBy(orderBy, desc);
                }
                if (groupBy != null && !groupBy.isEmpty()) {
                    selector.setGroupBy(groupBy);
                }
                return selector.findAll();
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite find all object error.", e);
        }
        return null;
    }

    public static <T> List<T> findAll(Selector<T> selector) {
        try {
            return selector.findAll();
        } catch (Exception e) {
            LogUtil.e("SqlLite find all by selector error.", e);
        }
        return null;
    }

    public static <T> Selector<T> selector(Class<T> entityType) {
        try {
            if (dbManager != null) {
                return dbManager.selector(entityType);
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite selector error.", e);
        }
        return null;
    }

    public static <T> T findById(Class<T> entityType, String idValue) {
        try {
            if (dbManager != null) {
                return dbManager.findById(entityType, idValue);
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite find object by id error.", e);
        }
        return null;
    }

    public static <T> T findFirst(Class<T> entityType, WhereBuilder wBuilder) {
        try {
            if (dbManager != null) {
                Selector<T> selector = dbManager.selector(entityType);
                if (wBuilder != null) {
                    selector.where(wBuilder);
                }
                return selector.findFirst();
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite find first object error.", e);
        }
        return null;
    }

    public static <T> T findFirst(Class<T> entityType, WhereBuilder wBuilder, String orderBy, boolean desc) {
        try {
            if (dbManager != null && tableIsExist(entityType)) {
                Selector<T> selector = dbManager.selector(entityType);
                if (wBuilder != null) {
                    selector.where(wBuilder);
                }
                if (orderBy != null) {
                    selector.orderBy(orderBy, desc);
                }
                return selector.findFirst();
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite find first object error.", e);
        }
        return null;
    }

    public static <T> T findFirst(Class<T> entityType) {
        return findFirst(entityType, null);
    }

    public static <T> long count(Class<T> entityType, WhereBuilder wBuilder) {
        int count = 0;
        try {
            if (dbManager != null) {
                return dbManager.selector(entityType).where(wBuilder).count();
            }
        } catch (Exception e) {
            LogUtil.e("SqlLite count error.", e);
        }
        return count;
    }

    public static <T> long count(Class<T> entityType) {
        return count(entityType, null);
    }


    public static synchronized boolean createTableIfNotExist(Class<?> entityType) {
        try {
            if (dbManager != null) {
                TableEntity<?> table = dbManager.getTable(entityType);
                if (!table.tableIsExist()) {
                    SqlInfo sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(table);
                    dbManager.execNonQuery(sqlInfo);

                    String execAfterTableCreated = table.getOnCreated();
                    if (!TextUtils.isEmpty(execAfterTableCreated)) {
                        dbManager.execNonQuery(execAfterTableCreated);
                    }
                }
                return true;
            }
        } catch (DbException e) {
            LogUtil.e("Create table error.", e);
        }
        return false;
    }
}
